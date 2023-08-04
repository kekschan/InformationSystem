import React, {Component} from 'react';
import {Button, Card} from 'react-bootstrap';
import axios from "axios";
import '../css/Train.css';
import passengerImage from '../resources/passenger.jpg'; // Импортируем путь к изображению
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLongArrowRight} from "@fortawesome/free-solid-svg-icons";

export default class Train extends Component {
    constructor(props) {
        super(props);
        this.state = {
            train: []
        };
    }

    findAllTrain() {
        axios.get("http://localhost:8080/train")
            .then(response => response.data)
            .then((data) => {
                this.setState({train: data});
            });
    }

    componentDidMount() {
        this.findAllTrain();
    }

    render() {
        return (
            <body>
            {/*сделать по-середине*/}
            <div>
            {this.state.train.length === 0 ?
                <div className="fade-in-card">
                    <Card className="red-border">
                        <Card.Body>
                            <div className="text-center">
                                <Card.Title>На данный момент нет добавленных поездов</Card.Title>
                            </div>
                        </Card.Body>
                    </Card>
                </div>
                :
                <div className="fade-in-card">
                    <Card className="red-border">
                        <Card.Header as="h5" className="card-header-container">
                            <div className="text-start">
                                <div style={{display: 'inline-block', marginRight: '10px'}}>№444A</div>
                                <div style={{display: 'inline-block'}}>Пассажирский</div>
                            </div>
                            <div className="text-end">
                                <div style={{display: 'inline-block', marginRight: '10px'}}>Москва</div>
                                <div style={{display: 'inline-block', marginRight: '10px'}}><FontAwesomeIcon icon={faLongArrowRight}></FontAwesomeIcon></div>
                                <div style={{display: 'inline-block'}}>Лондон</div>
                            </div>
                        </Card.Header>
                        <Card.Body>
                            <Card.Text> {/*вставить условие, если "trainType": "freight", то выводить одно */}
                                <img src={passengerImage} alt="Passenger"  style={{ width: '740px', height: '65px' }}/>
                                {/*вставить количество каждых из типов вагонов*/}
                            </Card.Text>
                        </Card.Body>
                        <Card.Footer className="card-footer-container">
                            <div className="text-start"><Button variant="primary">Подробнее</Button></div>
                            <div className="text-end"><Button variant="primary">Изменить</Button></div>
                        </Card.Footer>
                    </Card>
                </div>
            }
            </div>
            </body>


        );
    }
}
