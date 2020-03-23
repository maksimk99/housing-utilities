import React, {Component} from 'react';
import HousingComponent from "./HousingComponent";
import HousingDataService from "../service/HousingDataService";
import LocalDate from "localdate"

class UserCosts extends Component {
    defaultComponent = {
        tariff: "unknown",
        calculatedPrice: 0.0
    };

    constructor(props) {
        super(props);
        this.state = {
            totalPrice: 0,
            elevatorMaintenance: this.defaultComponent,
            maintenance: this.defaultComponent,
            overhaulService: this.defaultComponent,
            waterDisposal: this.defaultComponent,
            waterService: this.defaultComponent,
            electricityService: this.defaultComponent,
            listOfAvailableDates: null,
            currentDate: null
        };
        this.getElevatorMaintenanceData = this.getElevatorMaintenanceData.bind(this);
        this.getMaintenanceData = this.getMaintenanceData.bind(this);
        this.getOverhaulServiceData = this.getOverhaulServiceData.bind(this);
        this.getWaterDisposalData = this.getWaterDisposalData.bind(this);
        this.getWaterServiceData = this.getWaterServiceData.bind(this);
        this.getElectricityServiceData = this.getElectricityServiceData.bind(this);
        this.setListOfAvailableDates = this.setListOfAvailableDates.bind(this);
        this.convertDateToCustomFormat = this.convertDateToCustomFormat.bind(this);
        this.getCosts = this.getCosts.bind(this);
    }

    componentDidMount() {
        this.getElevatorMaintenanceData(new LocalDate());
        this.getMaintenanceData(new LocalDate());
        this.getOverhaulServiceData(new LocalDate());
        this.getWaterDisposalData(new LocalDate());
        this.getWaterServiceData(new LocalDate());
        this.getElectricityServiceData(new LocalDate());
    }

    setListOfAvailableDates(data) {
        let {listOfAvailableDates, currentDate} = this.state;
        if (listOfAvailableDates == null && data.listOfAvailableDates != null) {
            this.setState({
                listOfAvailableDates: data.listOfAvailableDates,
            })
        }
        if (currentDate == null && data.currentDate != null) {
            this.setState({
                currentDate: data.currentDate
            })
        }
    }

    getElevatorMaintenanceData(date) {
        HousingDataService.getElevatorMaintenanceData(date).then(response => {
            if (response != null) {
                this.setState({
                    elevatorMaintenance: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getMaintenanceData(date) {
        HousingDataService.getMaintenanceData(date).then(response => {
            if (response != null) {
                this.setState({
                    maintenance: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getOverhaulServiceData(date) {
        HousingDataService.getOverhaulServiceData(date).then(response => {
            if (response != null) {
                this.setState({
                    overhaulService: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getWaterDisposalData(date) {
        HousingDataService.getWaterDisposalData(date).then(response => {
            if (response != null) {
                this.setState({
                    waterDisposal: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getWaterServiceData(date) {
        HousingDataService.getWaterServiceData(date).then(response => {
            if (response != null) {
                this.setState({
                    waterService: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getElectricityServiceData(date) {
        HousingDataService.getElectricityServiceData(date).then(response => {
            if (response != null) {
                this.setState({
                    electricityService: response.data,
                    totalPrice: this.state.totalPrice + response.data.calculatedPrice
                });
                this.setListOfAvailableDates(response.data)
            }
        })
    }

    getCosts(data) {
        this.setState({
            totalPrice: 0,
            elevatorMaintenance: this.defaultComponent,
            maintenance: this.defaultComponent,
            overhaulService: this.defaultComponent,
            waterDisposal: this.defaultComponent,
            waterService: this.defaultComponent,
            electricityService: this.defaultComponent,
            listOfAvailableDates: null,
            currentDate: null
        });
        let date = data.target.dataset.date;
        this.getElevatorMaintenanceData(date);
        this.getMaintenanceData(date);
        this.getOverhaulServiceData(date);
        this.getWaterDisposalData(date);
        this.getWaterServiceData(date);
        this.getElectricityServiceData(date);
    }

    convertDateToCustomFormat(date) {
        let dateToConvert = new LocalDate(date);
        return dateToConvert.getMonth() + "." + dateToConvert.getYear();
    }

    render() {
        let {
            elevatorMaintenance, maintenance, overhaulService, waterDisposal,
            waterService, electricityService, totalPrice, listOfAvailableDates, currentDate
        } = this.state;
        return (
            <div className="col-8 text-center p-5 border bg-light rounded shadow">
                <h1>User Costs</h1>
                <ul className="p-0">{listOfAvailableDates != null ? listOfAvailableDates.map(date =>
                        <button className={currentDate === date ? "btn btn-primary m-1" : "btn btn-secondary m-1"}
                                type="button" key={date} data-date={date} onClick={this.getCosts}>
                            {this.convertDateToCustomFormat(date)}
                        </button>) : ""}
                </ul>
                <div className="row">
                    <div className="col-4 p-4 mt-4 border rounded" key="Maintenance">
                        <HousingComponent name="Maintenance" tariff={maintenance.tariff}
                                          calculatedPrice={maintenance.calculatedPrice}/>
                    </div>
                    <div className="col-4 p-4 mt-4 border rounded" key="Elevator Maintenance">
                        <HousingComponent name="Elevator Maintenance" tariff={elevatorMaintenance.tariff}
                                          calculatedPrice={elevatorMaintenance.calculatedPrice}/>
                    </div>
                    <div className="col-4 p-4 mt-4 border rounded" key="Overhaul Service">
                        <HousingComponent name="Overhaul Service" tariff={overhaulService.tariff}
                                          calculatedPrice={overhaulService.calculatedPrice}/>
                    </div>

                    <div className="col-4 p-4 mt-4 border rounded" key="Water Disposal">
                        <HousingComponent name="Water Disposal" tariff={waterDisposal.tariff}
                                          calculatedPrice={waterDisposal.calculatedPrice}/>
                    </div>
                    <div className="col-4 p-4 mt-4 border rounded" key="Water Service">
                        <HousingComponent name="Water Service" tariff={waterService.tariff}
                                          calculatedPrice={waterService.calculatedPrice}/>
                    </div>
                    <div className="col-4 p-4 mt-4 border rounded" key="Electricity Service">
                        <HousingComponent name="Electricity Service" tariff={electricityService.tariff}
                                          calculatedPrice={electricityService.calculatedPrice}/>
                    </div>
                </div>
                <div className="pt-4">
                    <h3>TotalPrice: {totalPrice.toFixed(2)}p</h3>
                </div>
            </div>
        )
    }
}

export default UserCosts;