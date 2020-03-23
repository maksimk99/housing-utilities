import React, {Component} from 'react'
import AuthenticationService from '../service/AuthenticationService';
import {ErrorMessage, Field, Form, Formik} from "formik";

class LoginComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            hasLoginFailed: false,
        };
        this.onSubmit = this.onSubmit.bind(this);
        this.validate = this.validate.bind(this)
        this.logoutClicked = this.logoutClicked.bind(this);
    }

    logoutClicked() {
        AuthenticationService.logout();
        this.props.history.push(`/login`)
    }

    onSubmit(values) {
        AuthenticationService
            .executeBasicAuthenticationService(values.username, values.password)
            .then((response) => {
                console.log("authentication response")
                AuthenticationService.registerSuccessfulLogin(values.username, response.data);
                window.location = "/user/costs";
            }).catch(() => {
            this.setState({username: values.username, password: values.password, hasLoginFailed: true});
        });
        console.log("on submit method")
    }

    validate(values) {
        let errors = {};
        if (!values.username || values.username.trim().length <= 0) {
            errors.username = 'Please enter user name';
        }
        if (!values.password || values.password.trim().length <= 0) {
            errors.password = 'Please enter password';
        }
        return errors
    }

    render() {
        let {username, password, hasLoginFailed} = this.state;
        return (
            <div className="col col-sm-6 col-md-6 col-lg-4 col-xl-3 text-center p-5 border bg-light rounded shadow">
                <h1 className="h2 mb-3">Please sign in</h1>
                <Formik initialValues={{username, password, hasLoginFailed}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}>
                    {
                        (props) => (
                            <Form>
                                {hasLoginFailed && <div className="alert alert-warning">Invalid Credentials</div>}
                                <ErrorMessage name="username" component="div" className="alert alert-warning"/>
                                <ErrorMessage name="password" component="div" className="alert alert-warning"/>
                                <fieldset className="form-group text-left">
                                    <label htmlFor="username">User name</label>
                                    <Field className="form-control" type="text" name="username" id="username"/>
                                </fieldset>
                                <fieldset className="form-group text-left">
                                    <label htmlFor="password">Password</label>
                                    <Field className="form-control" type="password" name="password" id="password"/>
                                </fieldset>
                                <button className="btn btn-lg btn-primary btn-block" type="submit">Login</button>
                            </Form>
                        )
                    }
                </Formik>
                <a href="http://localhost:8090/register"
                   className="btn btn-lg btn-secondary btn-block mt-2">Don't have account? Sing up</a>
                <p className="mt-5 mb-3 text-muted">© 2019-2020</p>
            </div>
        )
    }
}

export default LoginComponent