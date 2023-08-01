import './App.css';
import {BrowserRouter as Router} from 'react-router-dom'
import { Container, Row} from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";

export default function App() {
    return (
        <Router>
            <NavigationBar/>
            <Container >
                <Row>

                </Row>
            </Container>
        </Router>
    );
}
