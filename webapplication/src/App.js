import './App.css';
import {BrowserRouter as Router} from 'react-router-dom'
import { Container, Row} from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";
import Footer from "./components/Footer";

export default function App() {
    return (
        <Router>
            <NavigationBar/>
            <Container >
                <Row>

                </Row>
            </Container>
            <Footer/>
        </Router>
    );
}
