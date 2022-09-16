import React, { useState, useReducer, useContext } from "react"

import { Button, Form, Modal, InputGroup } from "react-bootstrap"
import { RegisterContext } from "../hooks/context/RegisterContext"
import { ToastContext } from "../hooks/context/ToastContext"
import { UserContext } from "../hooks/context/UserContext"
import { UsersAPI } from "../data/UsersAPI"
import { UsersActions } from "../hooks/reducer/actions/UsersActions"
import { UsersReducer, User } from "../hooks/reducer/UsersReducer"


const RegisterModal = () => {
  const registerContext  = useContext(RegisterContext)
  const toastContext  = useContext(ToastContext)
  const userContext  = useContext(UserContext)
  const dispatch = useReducer(UsersReducer, [])[1]
  const userDefault = {name: "", gender: "", age: 50}
  const [user, setUser] = useState(userDefault)
  const [validated, setValidated] = useState(false)

  const userChange = (event: any) => {
    event.persist()
    setUser((prevProfile) => ({...prevProfile, [event.target.name]: event.target.value}))
  }

  const createUser = (user: User) => {
    UsersAPI.createUser(user)
      .then(response => {
        dispatch(UsersActions.setUser(response))
        userContext.updateUser({...userContext.user, registered: true, ...response})
        toastContext.onToastAdd({show: true, title: "Success", message: "You have successfully logged in!", color: "success"})
      })
      .catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
    registerContext.onRegisterShow(false)
    setUser(userDefault)
  }

  const handleSubmit = (event: any) => {
    if (event.currentTarget.checkValidity() === false) {
      setValidated(true)
      event.stopPropagation()
    } else {
      setValidated(false)
      createUser(user)
    }
    event.preventDefault()
  }

  return (
    <Modal show={registerContext.register.show} onHide={() => registerContext.onRegisterShow(false)}>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <Modal.Header closeButton>
          <Modal.Title>Register</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group>
            <Form.Label>Name:</Form.Label>
            <InputGroup>
              <InputGroup.Text id="inputGroupPrepend">@</InputGroup.Text>
              <Form.Control required name="name" type="text" minLength={2} maxLength={10} placeholder="Name" value={user.name} onChange={userChange}/>
              <Form.Control.Feedback type="valid">Looks good!</Form.Control.Feedback>
              <Form.Control.Feedback type="invalid">Name must be between 2 and 10 characters long!</Form.Control.Feedback>
            </InputGroup>
          </Form.Group>
          <hr/>
          <Form.Group>
            <Form.Label>Gender:</Form.Label>
            <InputGroup>
              <Form.Select required name="gender" value={user.gender} onChange={userChange}>
                <option value=""></option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
              </Form.Select>
              <Form.Control.Feedback type="valid">Looks good!</Form.Control.Feedback>
              <Form.Control.Feedback type="invalid">Select your gender!</Form.Control.Feedback>
            </InputGroup>
          </Form.Group>
          <hr/>
          <Form.Group>
            <Form.Label>Age: {user.age}</Form.Label>
            <Form.Range required name="age" min="5" max="120" onChange={userChange}/>
            <Form.Control.Feedback type="valid">Looks good!</Form.Control.Feedback>
            <Form.Control.Feedback type="invalid">Select your age!</Form.Control.Feedback>
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick = {() => registerContext.onRegisterShow(false)}>Close</Button>
          <Button variant="primary" type="submit">Register</Button>
        </Modal.Footer>
      </Form>
    </Modal>
  )
}

export default RegisterModal