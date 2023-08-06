import React, { Component } from "react";
import { Card } from "react-bootstrap";
import { withRouter } from "react-router-dom";
import axios from "axios";

class Wagon extends Component {
    constructor(props) {
        super(props);
        this.state = {
            trainData: null,
        };
    }

    componentDidMount() {
        // Получение id поезда из URL
        const { id } = this.props.match.params;

        // Отправка GET-запроса на сервер для получения данных поезда
        axios
            .get(`http://localhost:8080/train/${id}`)
            .then((response) => {
                // Обработка успешного ответа
                const trainData = response.data;
                this.setState({ trainData });
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
                    <div style={{ display: 'inline-block', marginBlockEnd: '15px', lineHeight: '1.5' }}>
                        <Card className="custom-card">
                            <Card.Header as="h4">
                                <div className="text-center">
                                    <div style={{ marginBlockEnd: '5px' }}>Загрузка...</div>
                                </div>
                            </Card.Header>
                        </Card>
                    </div>
                </div>
                </body>
            );
        }

        // Извлечение данных поезда из состояния
        const { trainName, trainType, startingPoint, finishPoint, numberOfWagons } = this.state.trainData;

        return (
            <body style={styles.cardContainer}>
            <div>
                <div style={{ display: 'inline-block', marginBlockEnd: '15px', lineHeight: '1.5' }}>
                    <Card className="custom-card">
                        <Card.Header as="h4">
                            <div className="text-center">
                                <div style={{ marginBlockEnd: '5px' }}>
                                    {trainType === 'freight' ? "Грузовой" : "Пассажирский"} поезд с номером {trainName}; {startingPoint} - {finishPoint}; Количество вагонов: {numberOfWagons}
                                </div>
                                <div style={{ marginBlockEnd: '20px' }}>Здесь вы можете добавлять, удалять и изменять
                                    вагоны у данного поезда. Удачной вам работы!
                                </div>
                            </div>
                        </Card.Header>
                    </Card>
                </div>
            </div>
            </body>
        );
    }
}

export default withRouter(Wagon);
