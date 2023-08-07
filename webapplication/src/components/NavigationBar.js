import { Navbar, Container, Nav } from 'react-bootstrap';
import '../css/NavigationBar.css';
import {Component} from "react";

export default class NavigationBar extends Component {
    render() {
        return (
            <Navbar className="custom-navbar">
                <Container>
                    <Navbar.Collapse id="navbar-nav">
                        <Nav className="mx-auto spaced-links">
                            <Nav.Link href="/main">Главная</Nav.Link>
                            <Nav.Link href="/train">Информационная модель</Nav.Link>
                            <Nav.Link href="/static">Статистика</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        );
    }
}
