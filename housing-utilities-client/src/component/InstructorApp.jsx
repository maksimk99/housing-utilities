import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import UserCosts from "./UserCosts"
import LoginComponent from "./LoginComponent";
import ProtectedRoute from "./ProtectedRoute";
import HomePage from "./HomePage";

class InstructorApp extends Component {

    render() {
        return (
            <Router>
                <>
                    <Switch>
                        <Route path="/" exact component={HomePage}/>
                        <Route path="/home" exact component={HomePage}/>
                        <Route path="/login" exact component={LoginComponent}/>
                        <ProtectedRoute path="/user/costs" component={UserCosts}/>
                    </Switch>
                </>
            </Router>
        )
    }
}

export default InstructorApp;