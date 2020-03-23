import React, {Component} from 'react'

class HomePage extends Component {

    render() {
        return (
            <div className="col col-sm-6 col-md-6 col-lg-4 col-xl-3 text-center p-5 border bg-light rounded shadow">
                <h1 className="h2 mb-3">Home page</h1>
                <h3><a href="http://localhost:3000/user/costs">You can check your costs</a></h3>
                <p className="mt-5 mb-3 text-muted">© 2019-2020</p>
            </div>
        )
    }
}

export default HomePage