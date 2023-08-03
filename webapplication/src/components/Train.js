import React, {Component} from 'react';
import {Button, Card} from 'react-bootstrap';
import axios from "axios";

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
            .then((date) => {
                this.setState({train: date})
            });
    }

    componentDidMount() {
        this.findAllTrain();
    }

    render() {
        return (
            <body>
            {this.state.train.length === 0 ?
                <div>
                    Нет добавленных поездов
                </div>
                :
                <div>
                    <Card>
                        <Card.Header as="h5">Featured</Card.Header>
                        <Card.Body>
                            <Card.Title>Special title treatment</Card.Title>
                            <Card.Text>
                                With supporting text below as a natural lead-in to additional content.
                            </Card.Text>
                            <Button variant="primary">Go somewhere</Button>
                        </Card.Body>
                    </Card>
                </div>
            }
            </body>

        )
    }
}