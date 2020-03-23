import React, {Component} from 'react';
import {Redirect, Route} from "react-router-dom";
import AuthorizationService from "../service/AuthenticationService"

class ProtectedRoute extends Component {
    render() {
        const {component: Component, ...props} = this.props;
        return (
            <Route
                {...props}
                render={props => (
                    AuthorizationService.isUserLoggedIn() ?
                        <Component {...props} /> :
                        <Redirect to='/login'/>
                )}
            />
        )
    }
}

export default ProtectedRoute;
