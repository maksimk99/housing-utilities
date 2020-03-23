import axios from 'axios';

const GATEWAY_SERVER_URL = "http://localhost:8089"
const ELEVATOR_MAINTENANCE_API_URL = `${GATEWAY_SERVER_URL}/elevator-maintenance-api/elevator/maintenance/user`;
const MAINTENANCE_SERVICE_API_URL = `${GATEWAY_SERVER_URL}/maintenance-service-api/maintenance/user`;
const OVERHAUL_SERVICE_API_URL = `${GATEWAY_SERVER_URL}/overhaul-service-api/overhaul/user`;
const WATER_DISPOSAL_API_URL = `${GATEWAY_SERVER_URL}/water-disposal-api/water/disposal/user`;
const WATER_SERVICE_API_URL = `${GATEWAY_SERVER_URL}/water-service-api/water/user`;
const ELECTRICITY_SERVICE_API_URL = `${GATEWAY_SERVER_URL}/electricity-service-api/electricity/user`;

class HousingDataService {

    getElevatorMaintenanceData(date) {
        return axios.get(`${ELEVATOR_MAINTENANCE_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getElevatorMaintenanceData failed: " + any);
            return null
        });
    }

    getMaintenanceData(date) {
        return axios.get(`${MAINTENANCE_SERVICE_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getMaintenanceData failed: " + any);
            return null
        });
    }

    getOverhaulServiceData(date) {
        return axios.get(`${OVERHAUL_SERVICE_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getOverhaulServiceData failed: " + any);
            return null
        });
    }

    getWaterDisposalData(date) {
        return axios.get(`${WATER_DISPOSAL_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getWaterDisposalData failed: " + any);
            return null
        });
    }

    getWaterServiceData(date) {
        return axios.get(`${WATER_SERVICE_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getWaterServiceData failed: " + any);
            return null
        });
    }

    getElectricityServiceData(date) {
        return axios.get(`${ELECTRICITY_SERVICE_API_URL}/calculate?date=${date}`).catch(any => {
            console.log("getElectricityServiceData failed: " + any);
            return null
        });
    }
}

export default new HousingDataService();