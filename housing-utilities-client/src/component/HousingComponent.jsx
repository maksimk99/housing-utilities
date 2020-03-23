import React from 'react';

function HousingComponent(props) {
    return (
        <div>
            <h4>{props.name}</h4>
            <hr/>
            <h5>Tariff: <small>{props.tariff}</small></h5>
            <h5>Total price: <small className="bg-info">{props.calculatedPrice}p</small></h5>
        </div>
    )
}

export default HousingComponent;