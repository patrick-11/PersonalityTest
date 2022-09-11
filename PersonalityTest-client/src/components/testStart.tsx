import React, { useContext } from "react"

import { Button, Row, Col } from "react-bootstrap"
import { RegisterContext } from "../hooks/context/registerContext"
import { UserContext } from "../hooks/context/userContext"


const TestStart = () => {
	const registerContext  = useContext(RegisterContext)
	const userContext  = useContext(UserContext)

	return (
		<Row>
			<Col className="text-center">
				{userContext.user.registered ?
					<Button size="lg" variant="success" onClick={() => userContext.updateUser({...userContext.user, inTest: true})}>Start Test</Button>
					:
					<Button size="lg" variant="primary" onClick={() => registerContext.onRegisterShow(true)}>Register</Button>
				}
			</Col>
		</Row>
	)
}

export default TestStart