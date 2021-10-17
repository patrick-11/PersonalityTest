import React from "react"


import { Navbar, Container, Nav, Button, NavDropdown } from "react-bootstrap"
import { Link } from "react-router-dom"

import { HeaderProps } from "../types/interfacesRouter"


const header = (props: HeaderProps) => {
	const title = "Logged in as: " + props.user.name

	return (
		<Navbar expand = "md" bg = "secondary" variant = "dark">
			<Container>
				<Navbar.Brand as = {Link} to = "/PersonalityTest">Personality Test</Navbar.Brand>
				<Navbar.Toggle/>

				<Navbar.Collapse>
					<Nav className = "me-auto">
						<Nav.Link as = {Link} to = "/Test">Test</Nav.Link>
						<Nav.Link as = {Link} to = "/Results">Results</Nav.Link>
						<Nav.Link as = {Link} to = "/Search">Search</Nav.Link>
					</Nav>

					<Nav>
						{props.user.name.length ?
							props.inTest ?
								<>
								<Nav.Link as = {Link} to = "/test">My Test</Nav.Link>
								<NavDropdown title = {title} id="dropdown-basic">
									<NavDropdown.Item onClick = {() => props.onLogout()}>Sign out</NavDropdown.Item>
								</NavDropdown>
								</>
								:
								<NavDropdown title = {title} id="dropdown-basic">
									<NavDropdown.Item onClick = {() => props.onLogout()}>Sign out</NavDropdown.Item>
								</NavDropdown>
							:
							<Button onClick = {() => props.onRegisterShow(true)}>Register</Button>
						}
					</Nav>
				</Navbar.Collapse>
			</Container>
		</Navbar>
	)
}

export default header