import React, {Component} from 'react';
import {Button, Card, Row, Col, Container} from 'react-bootstrap';
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

        const styles = {
            cardContainer: {
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
                
            },
        };

        return (
            <body  style={styles.cardContainer}>
                <div >
                    <div style={{ display: 'inline-block', marginBlockEnd: '15px', fontSize: '24px', lineHeight: '1.5' }}>
                        <Card className="custom-card">
                            <Card.Header as="h4">
                            <div className="text-center">
                                <div style={{marginBlockEnd: '5px'}}>Добро пожаловать на страницу системного администрирования.</div>
                                <div>Здесь вы можете добавлять, удалять и изменять поезда на сервере. Удачной вам работы!</div>
                            </div>
                            </Card.Header>
                            <Card.Footer className="text-end">
                            <Button variant="success">Добавить</Button>
                            </Card.Footer>
                        </Card>
                        </div>
                    <div>
                        {this.state.train.length === 0 ?
                            (<div className="fade-in-card">
                                <Card className="red-border">
                                    <Card.Body>
                                        <div className="text-center">
                                            <Card.Title>На данный момент нет добавленных поездов</Card.Title>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </div>) 
                            : (
                            <div className="fade-in-card">
                                <Card className="red-border">
                                        <Card.Header as="h5" className="card-header-container">
                                            <div className="text-start">
                                                <div style={{display: 'inline-block', marginRight: '10px'}}>№444A</div>
                                                <div style={{display: 'inline-block'}}>Пассажирский</div>
                                            </div>
                                            <div className="text-end d-md-block d-none">
                                                <div style={{display: 'inline-block', marginRight: '10px'}}>Москва</div>
                                                <div style={{display: 'inline-block', marginRight: '10px'}}><FontAwesomeIcon icon={faLongArrowRight}></FontAwesomeIcon></div>
                                                <div style={{display: 'inline-block'}}>Лондон</div>
                                            </div>
                                        </Card.Header>
                                        <Card.Body style={{ height: '140px', display: 'flex', alignItems: 'center' }}>
                                            <Card.Text>
                                                <Container>
                                                    <Row>
                                                        <Col xxl="9" xl="9" lg="9" md="9">
                                                            <div className="d-flex justify-content-center">
                                                                <img
                                                                src={passengerImage}
                                                                alt="Passenger"
                                                                className="img-fluid d-md-block d-none"
                                                                style={{ width: '740px', height: '65px' }}/>
                                                            </div>
                                                        </Col>
                                                        <Col>
                                                        Сделать вывод через map 
                                                        </Col>
                                                    </Row>
                                                </Container>
                                            </Card.Text>
                                        </Card.Body>
                                        <Card.Footer className="card-footer-container">
                                            <div className="text-start">
                                                <Button variant="outline-danger light"className="custom-btn">Подробнее</Button>
                                            </div>
                                            <div className="text-end">
                                                <Button variant="danger">Удалить</Button>
                                                <Button variant="info">Изменить</Button>
                                            </div>
                                        </Card.Footer>
                                    </Card>
                                </div>
                             )
                        }
            
                    </div>
                </div>
            </body>
        );
    }
}
