import React, {Component} from "react";
import {Button, Card, Form, Modal} from "react-bootstrap";
import {withRouter} from "react-router-dom";
import axios from "axios";
import '../css/Train.css';

class Wagon extends Component {
    constructor(props) {
        super(props);
        this.state = {
            trainData: null,
            wagons: [],
            showModal: false,
            wagonData: {
                wagonType: "",
            },
        };
    }

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

    componentDidMount() {
        const {id} = this.props.match.params;
        axios
            .get(`http://localhost:8181/train/${id}`)
            .then((response) => {
                const trainData = response.data;
                this.setState({trainData});
                if (trainData.trainType === "passenger") {
                    this.getPassengerWagons(id);
                } else if (trainData.trainType === "freight") {
                    this.getFreightWagons(id);
                }
            })
            .catch((error) => {
                // Обработка ошибок
                console.error("Ошибка:", error);
            });
    }

    getPassengerWagons(trainId) {
        axios
            .get(`http://localhost:8181/passenger/${trainId}`)
            .then((response) => {
                // Обработка успешного ответа
                const passengerWagons = response.data;
                this.setState({wagons: passengerWagons});
            })
            .catch((error) => {
                // Обработка ошибок
                console.error("Error:", error);
            });
    }

    getFreightWagons(trainId) {
        axios
            .get(`http://localhost:8181/freight/${trainId}`)
            .then((response) => {
                // Обработка успешного ответа
                const freightWagons = response.data;
                this.setState({wagons: freightWagons});
            })
            .catch((error) => {
                // Обработка ошибок
                console.error("Error:", error);
            });
    }

// Открыть модальное окно
    handleModalOpen = () => {
        this.setState({
            showModal: true,
            wagonData: {
                wagonType: "",
            },
        });
    };

// Закрыть модальное окно
    handleModalClose = () => {
        this.setState({ showModal: false });
    };

// Обработчик изменения типа вагона
    handleWagonTypeChange = (value) => {
        this.setState((prevState) => ({
            wagonData: {
                ...prevState.wagonData,
                wagonType: value,
            },
        }));
    };

    handleDeleteWagon = (wagonId) => {
        const { trainData } = this.state;
        const trainId = trainData.id;

        const url = trainData.trainType === 'freight'
            ? `http://localhost:8181/freight/${trainId}/${wagonId}`
            : `http://localhost:8181/passenger/${trainId}/${wagonId}`;

        // Make the DELETE request
        axios
            .delete(url)
            .then((response) => {
                console.log('Wagon deleted successfully:', response.data);
                if (trainData.trainType === 'freight') {
                    this.getFreightWagons(trainId);
                } else {
                    this.getPassengerWagons(trainId);
                }
            })
            .catch((error) => {
                console.error('Error deleting wagon:', error);
            });
    };

    handleSubmit = () => {
        const { wagonType } = this.state.wagonData;
        const { trainData } = this.state;
        const trainId = trainData.id;

        const postData = {
            wagonType: wagonType,
        };

        const url = trainData.trainType === 'freight'
            ? `http://localhost:8181/freight/${trainId}/add`
            : `http://localhost:8181/passenger/${trainId}/add`;

        // Make the POST request
        axios
            .post(url, postData)
            .then((response) => {

                console.log('Wagon added successfully:', response.data);

                if (trainData.trainType === 'freight') {
                    this.getFreightWagons(trainId);
                } else {
                    this.getPassengerWagons(trainId);
                }
                this.handleModalClose();
            })
            .catch((error) => {
                console.error('Error adding wagon:', error);
            });
    };



    render() {
        const styles = {
            cardContainer: {
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            },
        };

        if (!this.state.trainData) {
            return (
                <body style={styles.cardContainer}>
                <div>
                    <div style={{display: 'inline-block', marginBlockEnd: '15px', lineHeight: '1.5'}}>
                        <Card className="custom-card">
                            <Card.Header as="h4">
                                <div className="text-center">
                                    <div style={{marginBlockEnd: '5px'}}>Загрузка...</div>
                                </div>
                            </Card.Header>
                        </Card>
                    </div>
                </div>
                </body>
            );
        }

        const {wagons} = this.state;
        const {trainName, trainType, startingPoint, finishPoint, numberOfWagons} = this.state.trainData;

        return (
            <body style={styles.cardContainer}>
            <div>
                <div>
                    <div style={{display: 'inline-block', marginBlockEnd: '15px', lineHeight: '1.5'}}>
                        <Card className="custom-card">
                            <Card.Header as="h4">
                                <div className="text-center">
                                    <div style={{marginBlockEnd: '5px'}}>
                                        {trainType === 'freight' ? "Грузовой" : "Пассажирский"} поезд с
                                        номером {trainName}; {startingPoint} - {finishPoint}; Количество
                                        вагонов: {numberOfWagons}
                                    </div>
                                    <div
                                        style={{marginBlockEnd: '20px'}}>Здесь вы можете добавлять и удалять вагоны у данного
                                        поезда. Удачной вам работы!
                                    </div>
                                    <div className='text-end'><Button variant="success"
                                                                      onClick={this.handleModalOpen}>Добавить</Button>
                                    </div>
                                </div>
                            </Card.Header>
                        </Card>
                    </div>
                </div>

                <Modal show={this.state.showModal} onHide={this.handleModalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Выберите тип вагона</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="wagonType">
                                {this.state.trainData.trainType === 'freight' ? (
                                    <>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'gondola' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('gondola')}
                                            className="mb-2"  style={{marginRight: "4px"}}
                                        >
                                            Полувагон
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'covered' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('covered')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Крытый
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'tank' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('tank')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Цистерновый
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'flatcar' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('flatcar')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Вагон-платформа
                                        </Button>
                                    </>
                                ) : (
                                    <>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'bar' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('bar')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Бар
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'lux' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('lux')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Люкс
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'baggageLetters' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('baggageLetters')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Почтовый
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'coupe' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('coupe')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Купе
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'standartRestaurant' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('standartRestaurant')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Ресторан
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'envelopeLetters' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('envelopeLetters')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Почтовый (багаж)
                                        </Button>
                                        <Button
                                            variant={this.state.wagonData.wagonType === 'reservedSeat' ? 'danger' : 'outline-danger'}
                                            onClick={() => this.handleWagonTypeChange('reservedSeat')}
                                            className="mb-2" style={{marginRight: "4px"}}
                                        >
                                            Плацкарт
                                        </Button>
                                    </>
                                )}
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="primary" onClick={this.handleSubmit}>
                            Добавить
                        </Button>
                    </Modal.Footer>
                </Modal>
                <div>
                    {wagons.length === 0 ? (
                        <div className="fade-in-card">
                            <Card className="red-border">
                                <Card.Body>
                                    <div className="text-center">
                                        <Card.Title>Вагоны пока не добавлены!</Card.Title>
                                    </div>
                                </Card.Body>
                            </Card>
                        </div>
                    ) : (
                        <div className="fade-in-card card-grid" >
                            {wagons.map((wagon, index) => (
                                <Card className="custom-card" style={{marginBlockEnd: "15px"}} key={wagon.id} >
                                    <Card.Header as="h5">
                                        <div style={{display: 'inline-block', marginRight: '17px'}}>
                                            {index}
                                        </div>
                                        {wagon.train.trainType === "freight" ? this.getWagonTypeFreight(wagon.wagonType) : this.getWagonTypePassenger(wagon.wagonType)}
                                    </Card.Header>
                                    <Card.Body >
                                        <ul >
                                            {wagon.train.trainType === "freight" ? (
                                                <div>
                                                    Объем: {wagon.volume} м2.
                                                    <br/>
                                                    Длина: {wagon.length} м.
                                                    <br/>
                                                    Ширина: {wagon.width} м.
                                                    <br/>
                                                    Высота: {wagon.height} м.
                                                </div>
                                            ) : (
                                                <div>
                                                    Вместимость: {wagon.seatingCapacity} людей
                                                    <br/>
                                                    Количество столов: {wagon.tables}
                                                    <br/>
                                                    Наличие туалетов: {wagon.toilets ? "Да" : "Нет"}
                                                    <br/>
                                                    Наличие вентиляции: {wagon.hasVentilation ? "Да" : "Нет"}
                                                    {wagon.wagonType === "lux" &&
                                                        <div>Количество кроватей: {wagon.beds}</div>}
                                                    {wagon.wagonType === "bar" &&
                                                        <div>Наличие алкоголя: {wagon.hasAlcohol ? "Да" : "Нет"}</div>}
                                                    {wagon.wagonType === "baggageLetters" &&
                                                        <div>Вместимость писем: {wagon.accommodation}</div>}
                                                    {wagon.wagonType === "coupe" &&
                                                        <div>Количество кроватей: {wagon.beds}</div>}
                                                    {wagon.wagonType === "standartRestaurant" &&
                                                        <div>Наличие алкоголя: {wagon.hasAlcohol ? "Да" : "Нет"}</div>}
                                                    {wagon.wagonType === "envelopeLetters" &&
                                                        <div>Вместимость писем: {wagon.accommodation}</div>}
                                                    {wagon.wagonType === "reservedSeat" &&
                                                        <div>Количество кроватей: {wagon.beds}</div>}
                                                </div>
                                            )}
                                        </ul>
                                    </Card.Body>
                                    <Card.Footer key={wagon.id} className="card-footer-container">
                                        <div className="text-end">
                                            <Button className="custom-btn" style={{ marginRight: '3px' }} onClick={() => this.handleDeleteWagon(wagon.id)}>
                                                Удалить
                                            </Button>
                                        </div>
                                    </Card.Footer>
                                </Card>
                            ))}
                        </div>
                    )}
                </div>
            </div>
            </body>
        );
    }
}

export default withRouter(Wagon);
