import React, { useContext } from "react"

import { Navbar, Container, Nav, Button, NavDropdown } from "react-bootstrap"
import { Link } from "react-router-dom"
import { RegisterContext } from "../hooks/context/RegisterContext"
import { UserContext } from "../hooks/context/UserContext"


const Header = () => {
  const registerContext  = useContext(RegisterContext)
  const userContext  = useContext(UserContext)

  const title = "Logged in as: " + userContext.user.name

  return (
    <Navbar expand = "md" bg = "secondary" variant = "dark">
      <Container>
        <Navbar.Brand as = {Link} to={"/home"}>Personality Test</Navbar.Brand>
        <Navbar.Toggle/>

        <Navbar.Collapse>
          <Nav className = "me-auto">
            <Nav.Link as={Link} to={"/test"}>Test</Nav.Link>
            <Nav.Link as={Link} to={"/averages"}>Averages</Nav.Link>
            <Nav.Link as={Link} to={"/users"}>Users</Nav.Link>
            <Nav.Link as={Link} to={"/results"}>Results</Nav.Link>
            <Nav.Link as={Link} to={"/version"}>v2.0.0</Nav.Link>
          </Nav>

          <Nav>
            {userContext.user.registered ?
              userContext.user.inTest ?
                <>
                  <Nav.Link as={Link} to={"/test"}>My Test</Nav.Link>
                  <NavDropdown id="dropdown-basic" title={title}>
                    <NavDropdown.Item onClick={() => userContext.resetUser()}>Sign out</NavDropdown.Item>
                  </NavDropdown>
                </>
                :
                <NavDropdown title = {title} id="dropdown-basic">
                  <NavDropdown.Item onClick = {() => userContext.resetUser()}>Sign out</NavDropdown.Item>
                </NavDropdown>
              :
              <Button onClick = {() => registerContext.onRegisterShow(true)}>Register</Button>
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  )
}

export default Header