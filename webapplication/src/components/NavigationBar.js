import { Navbar, Container, Nav } from 'react-bootstrap';
import '../css/NavigationBar.css';
import {Component} from "react"; // Путь к вашему CSS-файлу или добавьте стили в соответствующий файл

export default class NavigationBar extends Component {
    render() {
        return (
            <Navbar className="custom-navbar">
                <Container>
                    <Navbar.Collapse id="navbar-nav">
                        <Nav className="mx-auto spaced-links">
                            <Nav.Link href="/train">Главная</Nav.Link>
                            <Nav.Link href="/inf-model">Информационная модель</Nav.Link>
                            <Nav.Link href="/static">Статистика</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        );
    }
}