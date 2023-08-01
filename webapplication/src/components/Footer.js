import React, { Component } from "react";
import { Col, Container, Navbar } from "react-bootstrap";
import '../css/Footer.css';

export default class Footer extends Component {
    render() {
        let fullYear = new Date().getFullYear();

        return (
            <Navbar fixed={"bottom"} style={{ backgroundColor: 'rgb(195, 27, 28)', opacity: 0.95}} variant={"dark"}>
                <Container className="footer-container">
                    <Col lg={12} className={"text-center text-white"}>
                        <div className="footer-content">2016-{fullYear}, Информационная система для отслеживания поездов</div>
                    </Col>
                </Container>
            </Navbar>
        );
    }
}
