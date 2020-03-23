import axios from 'axios';
import LocalStorageService from "./LocalStorageService";

const AUTH_SERVER_URL = "http://localhost:8090";
const queryString = require('query-string');
const localStorageService = LocalStorageService.getService();

class AuthenticationService {

    executeBasicAuthenticationService(username, password) {
        return axios.post(`${AUTH_SERVER_URL}/oauth/token`,
            queryString.stringify({
                "grant_type": "password",
                "username": username,
                "password": password
            }),
            {
                headers: {
                    Authorization: localStorageService.createBasicAuthToken(),
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }
        );
    }

    registerSuccessfulLogin(username, response) {
        localStorageService.setToken(response)
    }

    logout() {
        localStorageService.clearToken();
    }

    isUserLoggedIn() {
        return localStorageService.getAccessToken() != null;
    }
}

export default new AuthenticationService();