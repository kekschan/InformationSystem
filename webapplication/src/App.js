import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import {Col, Container, Row} from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";
import Footer from "./components/Footer";
import Train from "./components/Train";

export default function App() {

    const marginTop = {
        marginTop: "20px"
    };


    return (
        <Router>
            <NavigationBar/>
            <Container>
                <Row>
                    <Switch>
                        <Col lg={12} style={marginTop}>
                            <Route path={"/train"} exact component={Train}/>
                            <Route path={"/about"} exact component={Train}/>
                        </Col>
                    </Switch>
                </Row>
            </Container>
            <Footer/>
        </Router>
    );
}
