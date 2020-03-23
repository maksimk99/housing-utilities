import React from 'react';

import 'bootstrap/dist/css/bootstrap.min.css';
import InstructorApp from "./component/InstructorApp"
import axios from "axios";
import LocalStorageService from "../src/service/LocalStorageService";
import AuthenticationService from "./service/AuthenticationService";

const AUTH_SERVER_API_URL = "http://localhost:8090/oauth/token";
const queryString = require('query-string');
const localStorageService = LocalStorageService.getService();

function App() {
    return (
        <div className="row w-100" style={{"height": "100vh"}}>
            <div className="col">
                <div className="row justify-content-end align-items-center">
                    <div className="col-1 text-right">
                        {AuthenticationService.isUserLoggedIn() &&
                        <button className="btn btn-danger" onClick={logoutClicked}>logout</button>}
                    </div>
                </div>
                <div className="row justify-content-center align-items-center h-100">
                    <InstructorApp/>
                </div>
            </div>
        </div>
    );
}

function logoutClicked() {
    AuthenticationService.logout();
    window.location = "/login";
}

axios.interceptors.request.use(
    config => {
        const token = localStorageService.getAccessToken();
        if (token) {
            config.headers['Authorization'] = token;
        }
        return config;
    },
    error => {
        Promise.reject(error)
    });

axios.interceptors.response.use((response) => {
    return response
}, function (error) {
    const originalRequest = error.config;

    if ((error.response.status === 401 || error.response.status === 400)
        && originalRequest.url === AUTH_SERVER_API_URL && localStorageService.getAccessToken() == null) {
        return Promise.reject(error);
    }

    if ((error.response.status === 401 || error.response.status === 400) && !originalRequest._retry) {
        originalRequest._retry = true;
        const refreshToken = localStorageService.getRefreshToken();
        localStorageService.clearToken();
        return axios.post(AUTH_SERVER_API_URL,
            queryString.stringify({
                "grant_type": "refresh_token",
                "refresh_token": refreshToken
            }),
            {
                headers: {
                    Authorization: localStorageService.createBasicAuthToken(),
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }
        ).then(res => {
            if (res.status === 200) {
                localStorageService.setToken(res.data);
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorageService.getAccessToken();
                return axios(originalRequest);
            }
        }).catch((authError) => {
            if (((authError.response.status === 401 || authError.response.status === 400)
            && authError.config.url === AUTH_SERVER_API_URL)) {
                window.location = "/login";
                return Promise.reject(error);
            }
        })
    }
    return Promise.reject(error);
});

export default App;
