import React, {Component} from "react";
import {Card} from "react-bootstrap";
import {withRouter} from "react-router-dom";
import axios from "axios";

class Wagon extends Component {
    constructor(props) {
        super(props);
        this.state = {
            trainData: null,
            wagons: [], // Initialize wagons as an empty array
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
        // Получение id поезда из URL
        const {id} = this.props.match.params;

        // Отправка GET-запроса на сервер для получения данных поезда
        axios
            .get(`http://localhost:8080/train/${id}`)
            .then((response) => {
                // Обработка успешного ответа
                const trainData = response.data;
                this.setState({trainData});

                // Получение данных о вагонах в зависимости от типа поезда
                if (trainData.trainType === "passenger") {
                    this.getPassengerWagons(id);
                } else if (trainData.trainType === "freight") {
                    this.getFreightWagons(id);
                }
            })
            .catch((error) => {
                // Обработка ошибок
                console.error("Error:", error);
            });
    }

    getPassengerWagons(trainId) {
        // Отправка GET-запроса на сервер для получения данных о пассажирских вагонах
        axios
            .get(`http://localhost:8080/passenger/${trainId}`)
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
        // Отправка GET-запроса на сервер для получения данных о грузовых вагонах
        axios
            .get(`http://localhost:8080/freight/${trainId}`)
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

    render() {
        const styles = {
            cardContainer: {
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            },
        };

        // Если данные поезда еще не получены, показать загрузочный индикатор или другую информацию
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
        // Извлечение данных о вагонах из состояния
        const {wagons} = this.state;
        // Извлечение данных поезда из состояния
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
                                    <div style={{marginBlockEnd: '20px'}}>Здесь вы можете добавлять, удалять у данного поезда. Удачной вам работы!
                                    </div>
                                </div>
                            </Card.Header>
                        </Card>
                    </div>
                </div>
                <div>
                    {wagons.length === 0 ? (
                        <div className="fade-in-card">
                            <Card className="red-border">
                                <Card.Body>
                                    <div className="text-center">
                                        <Card.Title>Нет данных о вагонах для данного поезда.</Card.Title>
                                    </div>
                                </Card.Body>
                            </Card>
                        </div>
                    ) : (
                        <div className="fade-in-card">
                            {wagons.map((wagon) => (
                                <Card className="custom-card" style={{marginBlockEnd: "15px"}} key={wagon.id}>
                                    <Card.Header as="h5">
                                        {wagon.train.trainType === "freight" ? this.getWagonTypeFreight(wagon.wagonType) : this.getWagonTypePassenger(wagon.wagonType)}
                                    </Card.Header>
                                    <Card.Body>
                                        <ul>
                                                {wagon.train.trainType === "freight" ? (
                                                    <div>
                                                        Объем: {wagon.volume}
                                                        <br/>
                                                        Длина: {wagon.length}
                                                        <br/>
                                                        Ширина: {wagon.width}
                                                        <br/>
                                                        Высота: {wagon.height}
                                                    </div>
                                                ) : (
                                                    <div>
                                                        Вместимость: {wagon.seatingCapacity}
                                                        <br/>
                                                        Количество столов: {wagon.tables}
                                                        <br/>
                                                        Наличие туалетов: {wagon.toilets ? "Да" : "Нет"}
                                                        <br/>
                                                        Наличие вентиляции: {wagon.hasVentilation ? "Да" : "Нет"}
                                                        {wagon.wagonType === "lux" &&
                                                            <div>Количество кроватей: {wagon.beds}</div>}
                                                    </div>
                                                )}
                                        </ul>
                                    </Card.Body>
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
