import React, {Component} from 'react';
import {Button, Card, Row, Col, Container, Modal, Form, ToggleButtonGroup, ToggleButton} from 'react-bootstrap';
import axios from "axios";
import '../css/Train.css';
import passengerImage from '../resources/passenger.jpg';
import freightImage from '../resources/freight.jpg';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faLongArrowRight} from "@fortawesome/free-solid-svg-icons";


export default class Train extends Component {
    constructor(props) {
        super(props);
        this.state = {
            train: [],
            showModal: false,
            trainData: {
                trainName: "",
                trainType: "",
                startingPoint: "",
                finishPoint: "",
                numberOfWagons: 0,
            }
        };
    }


    // Удаление поезда по его идентификатору
    handleDeleteTrain = (trainId) => {
        // Отправка DELETE-запроса на сервер
        axios.delete(`http://localhost:8080/train/${trainId}`)
            .then(response => {
                // Обработка успешного ответа, если необходимо
                console.log(response.data);
                // Обновление списка поездов после удаления
                this.findAllTrain();
            })
            .catch(error => {
                // Обработка ошибок при отправке запроса
                console.error('Error:', error);
            });
    };

    // Открыть модальное окно
    handleModalOpen = () => {
        this.setState({
            showModal: true,
            trainData: { // Reset trainData to empty state
                trainName: "",
                trainType: "",
                startingPoint: "",
                finishPoint: "",
                numberOfWagons: 0,
            }
        });
    };

    // Закрыть модальное окно
    handleModalClose = () => {
        this.setState({showModal: false});
    };

    // Обработка изменений в полях ввода формы
    handleChange = (event) => {
        const {name, value} = event.target;
        this.setState(prevState => ({
            trainData: {
                ...prevState.trainData,
                [name]: value
            }
        }));
    };

    handleTrainTypeChange = (value) => {
        this.setState(prevState => ({
            trainData: {
                ...prevState.trainData,
                trainType: value
            }
        }));
    };

    // Обновление списка поездов с анимацией
    updateTrainListWithAnimation = () => {
        // Добавить класс show для плавного появления модального окна
        this.setState({showModal: true});

        // Задержка, чтобы показать модальное окно с анимацией
        setTimeout(() => {
            this.handleModalClose();
            this.findAllTrain();
        }, 200); // Измените значение задержки по вашему усмотрению
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {trainId, ...data} = this.state.trainData;

        if (trainId) {
            // If trainId exists, perform a PUT request for updating the train
            axios
                .put(`http://localhost:8080/train/${trainId}`, data)
                .then((response) => {
                    // Handle successful response, if needed
                    console.log(response.data);
                    this.updateTrainListWithAnimation();
                })
                .catch((error) => {
                    // Handle errors on PUT request
                    console.error('Error:', error);
                });
        } else {
            // If trainId doesn't exist, perform a POST request for creating a new train
            axios
                .post('http://localhost:8080/train/add', data)
                .then((response) => {
                    // Handle successful response, if needed
                    console.log(response.data);
                    this.updateTrainListWithAnimation();
                })
                .catch((error) => {
                    // Handle errors on POST request
                    console.error('Error:', error);
                });
        }
    };

    getWagonTypeFreight(wagonType) {
        const wagonTypeFreight = {
            gondola: 'Полувагон',
            covered: 'Крытый',
            tank: 'Цистерновый',
            flatcar: 'Вагон-платформа',
        };
        return wagonTypeFreight[wagonType] || wagonType;
    }

    getWagonTypePassenger(wagonType) {
        const wagonTypePassenger = {
            bar: 'Бар',
            lux: 'Люкс',
            baggageLetters: 'Почтовый',
            coupe: 'Купе',
            standartRestaurant: 'Ресторан',
            envelopeLetters: 'Почтовый (багаж)',
            reservedSeat: 'Плацкарт',
        };
        return wagonTypePassenger[wagonType] || wagonType;
    }


    // Получение данных поездов
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

    handleEditTrain = (train) => {
        this.setState({
            showModal: true,
            trainData: {
                trainId: train.id,
                trainName: train.trainName,
                trainType: train.trainType,
                startingPoint: train.startingPoint,
                finishPoint: train.finishPoint,
                numberOfWagons: train.numberOfWagons,
            },
        });
    };

    handleButtonClick = (trainId, trainType) => {
        this.props.history.push(`/${trainType}/${trainId}`);
    };

    countWagonTypes = (train) => {
        const wagonCounts = {};

        if (train.trainType === 'freight') {
            train.freightWagon.forEach((wagon) => {
                const wagonType = wagon.wagonType;
                if (wagonCounts[wagonType]) {
                    wagonCounts[wagonType] += 1;
                } else {
                    wagonCounts[wagonType] = 1;
                }
            });
        } else if (train.trainType === 'passenger') {
            train.peopleWagons.forEach((wagon) => {
                const wagonType = wagon.wagonType;
                if (wagonCounts[wagonType]) {
                    wagonCounts[wagonType] += 1;
                } else {
                    wagonCounts[wagonType] = 1;
                }
            });
        }

        return wagonCounts;
    };

    render() {
        const styles = {
            cardContainer: {
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'

            },
        };

        const wagonTypeLabels = {
            gondola: 'Полувагон',
            covered: 'Крытый',
            tank: 'Цистерновый',
            flatcar: 'Вагон-платформа',
            bar: 'Бар',
            lux: 'Люкс',
            baggageLetters: 'Почтовый',
            coupe: 'Купе',
            standartRestaurant: 'Ресторан',
            envelopeLetters: 'Почтовый (багаж)',
            reservedSeat: 'Плацкарт',
        };

        return (
            <body style={styles.cardContainer}>
            <div>
                <div style={{display: 'inline-block', marginBlockEnd: '15px', lineHeight: '1.5'}}>
                    <Card className="custom-card">
                        <Card.Header as="h4">
                            <div className="text-center">
                                <div style={{marginBlockEnd: '5px'}}>Добро пожаловать на страницу информационной модели.
                                </div>
                                <div style={{marginBlockEnd: '20px'}}>Здесь вы можете добавлять, удалять и изменять
                                    поезда на сервере. Удачной вам работы!
                                </div>
                                <div className='text-end'><Button variant="success"
                                                                  onClick={this.handleModalOpen}>Добавить</Button></div>
                            </div>
                        </Card.Header>
                    </Card>
                </div>
                {/* Модальное окно */}
                <Modal show={this.state.showModal} onHide={this.handleModalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>
                            {this.state.trainData.trainId ? "Обновить поезд" : "Добавить поезд"}
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form onSubmit={this.handleSubmit}>
                            <Form.Group controlId="trainName" style={{marginBlockEnd: "10px"}}>
                                <Form.Label style={{marginLeft: "8px", marginBlockEnd: "2px"}}>
                                    Название поезда
                                </Form.Label>
                                <Form.Control type="text"
                                              name="trainName"
                                              placeholder="Название поезда"
                                              value={this.state.trainData.trainName}
                                              onChange={this.handleChange}
                                              className="form-control-select"/>
                            </Form.Group>


                            {!this.state.trainData.trainId && (
                                <Form.Group controlId="trainType" className="form-group-container">
                                    <Form.Label className="form-label">Тип поезда</Form.Label>
                                    <div>
                                        <ToggleButtonGroup
                                            type="radio"
                                            name="trainType"
                                            value={this.state.trainData.trainType}
                                            onChange={this.handleTrainTypeChange}
                                            className="custom-toggle-button-group"
                                            defaultValue={"passenger"}
                                        >
                                            <ToggleButton value="passenger" id={"passenger"} variant="danger"
                                                          className="form-control-select custom-toggle-button">
                                                Пассажирский
                                            </ToggleButton>
                                            <ToggleButton value="freight" id={"freight"} variant="danger"
                                                          className="form-control-select custom-toggle-button">
                                                Грузовой
                                            </ToggleButton>
                                        </ToggleButtonGroup>
                                    </div>
                                </Form.Group>
                            )}

                            <Form.Group controlId="startingPoint" style={{marginBlockEnd: "10px"}}>
                                <Form.Label style={{marginLeft: "8px", marginBlockEnd: "2px"}}>
                                    Стартовая точка
                                </Form.Label>
                                <Form.Control type="text"
                                              name="startingPoint"
                                              placeholder="Стартовая точка"
                                              value={this.state.trainData.startingPoint}
                                              onChange={this.handleChange}
                                              className="form-control-select"/>
                            </Form.Group>
                            <Form.Group controlId="finishPoint" style={{marginBlockEnd: "10px"}}>
                                <Form.Label style={{marginLeft: "8px", marginBlockEnd: "2px"}}>
                                    Конечная точка
                                </Form.Label>
                                <Form.Control type="text"
                                              name="finishPoint"
                                              placeholder="Конечная точка"
                                              value={this.state.trainData.finishPoint}
                                              onChange={this.handleChange}
                                              className="form-control-select"/>
                            </Form.Group>
                            <Form.Group controlId="numberOfWagons" style={{marginBlockEnd: "10px"}}>
                                <Form.Label style={{marginLeft: "8px", marginBlockEnd: "7px"}}>
                                    Количество вагонов
                                </Form.Label>
                                <Form.Control type="number"
                                              name="numberOfWagons"
                                              placeholder="Количество вагонов"
                                              value={this.state.trainData.numberOfWagons}
                                              onChange={this.handleChange}
                                              className="form-control-select"/>
                            </Form.Group>
                            <div className={"text-end"} style={{marginBlockEnd: "-10px", marginTop: "15px"}}>
                                <Button variant="primary"
                                        type="submit">
                                    Отправить
                                </Button>
                            </div>
                        </Form>
                    </Modal.Body>
                </Modal>
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
                            {this.state.train.map((train) => (
                                <Card className="red-border" style={{marginBlockEnd: '15px'}} key={train.id}>
                                    <Card.Header as="h5" className="card-header-container">
                                        <div className="text-start">
                                            <div style={{
                                                display: 'inline-block',
                                                marginRight: '10px'
                                            }}>{train.trainName}</div>
                                            <div style={{display: 'inline-block'}}>
                                                {train.trainType === 'freight' ? 'Грузовой' : 'Пассажирский'}
                                            </div>
                                        </div>
                                        <div className="text-end d-md-block d-none">
                                            <div style={{
                                                display: 'inline-block',
                                                marginRight: '10px'
                                            }}>{train.startingPoint}</div>
                                            <div style={{display: 'inline-block', marginRight: '10px'}}>
                                                <FontAwesomeIcon icon={faLongArrowRight}></FontAwesomeIcon></div>
                                            <div style={{display: 'inline-block'}}>{train.finishPoint}</div>
                                        </div>
                                    </Card.Header>


                                    <Card.Body style={{maxHeight: '200px', display: 'flex', alignItems: 'center'}}>
                                        <Container>
                                            <Row>
                                                <Col lg="9" md="8" sm="12" xl={"8"} className="d-flex align-items-center">
                                                    <div className="d-flex justify-content-center">
                                                        <img
                                                            src={train.trainType === 'freight' ? freightImage : passengerImage}
                                                            alt={train.trainType === 'freight' ? 'Freight' : 'Passenger'}
                                                            className="img-fluid d-md-block d-none"
                                                            style={{
                                                                width: '100%', // The image will take the full width of its container
                                                                maxWidth: '700px', // Maximum width of 700px
                                                                height: '65px', // Default height of 65px
                                                                borderRadius: '5px',
                                                                boxShadow: '0 2px 5px rgba(0,0,0,0.0)',
                                                            }}
                                                        />
                                                    </div>
                                                </Col>
                                                <Col>
                                                    <div>
                                                        <Container className="train-label">
                                                            <Row>
                                                                <Col xs="10">
                                                                    Количество вагонов:
                                                                </Col>
                                                                <Col style={{fontWeight: 'bold'}}>
                                                                    {train.numberOfWagons}
                                                                </Col>
                                                            </Row>
                                                            {(train.peopleWagons && train.peopleWagons.length >= 0) || (train.freightWagon && train.freightWagon.length >= 0) ? (
                                                                    <div>
                                                                        {/* Вывод количества каждого типа вагона */}
                                                                        {Object.entries(this.countWagonTypes(train)).map(([wagonType, count]) => (
                                                                            <Row key={wagonType}>
                                                                                <Col xs={"10"}>
                                                                                    <li>{`${wagonTypeLabels[wagonType]}:`}</li>

                                                                                </Col>
                                                                                <Col>
                                                                                    {`${count}`}
                                                                                </Col>
                                                                            </Row>
                                                                        ))}
                                                                    </div>) :

                                                                (
                                                                    <div>Типы вагонов пока не добавлены</div>
                                                                )}
                                                        </Container>
                                                    </div>
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Card.Body>


                                    <Card.Footer key={train.id} className="card-footer-container">
                                        <div className="text-start">
                                            <div className="text-start">
                                                <Button className="custom-btn"
                                                        onClick={() => this.handleButtonClick(train.id, train.trainType)}>
                                                    Вагоны
                                                </Button>
                                            </div>
                                        </div>
                                        <div className="text-end">
                                            <div className="text-end">
                                                {/* Кнопка для удаления поезда */}
                                                <Button className="custom-btn" style={{marginRight: '3px'}}
                                                        onClick={() => this.handleDeleteTrain(train.id)}>Удалить</Button>
                                                <Button className="custom-btn"
                                                        onClick={() => this.handleEditTrain(train)}>Изменить</Button>
                                            </div>
                                        </div>
                                    </Card.Footer>
                                </Card>
                            ))}
                        </div>
                    }
                </div>
            </div>
            </body>
        );
    }
}
