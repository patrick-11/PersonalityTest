import React, { useState } from "react"

import { Table, InputGroup, Form, Stack, Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { User } from "../hooks/reducer/usersReducer"


interface UsersTableInterface {
	users: Array<User>,
	getUsers: () => void,
	updateUser: (user: User) => void
	deleteUser: (id: string) => void
 }

const UsersTable = (props: UsersTableInterface) => {
	let navigate = useNavigate()
	const filterDefault = {name: "", gender: "", ageMin: "5", ageMax: "120"}
	const modifyDefault = {id: "", name: "", gender: "", age: "50"}
	const [filter, setFilter] = useState(filterDefault)
	const [modify, setModify] = useState(modifyDefault)


	const filterUsers = (event: any) => {
		event.persist()
		setFilter((filter) => ({...filter, [event.target.name]: event.target.value}))
	}

	const modifyUser = (event: any) => {
		event.persist()
		setModify((modify) => ({...modify, [event.target.name]: event.target.value}))
	}

	const filterReset = () => {setFilter(filterDefault)}

	const modifyReset = () => {setModify(modifyDefault)}

	const users = filter !== filterDefault ?
		props.users.filter((user: User) => {
			return user.name.indexOf(filter.name) !== -1 &&
			(filter.gender.length === 0 ?
				user.gender === "Male" || user.gender === "Female" : user.gender === filter.gender
			) &&
			user.age >= parseInt(filter.ageMin) &&
			user.age <= parseInt(filter.ageMax)
		}) : props.users

	return (
			<Table striped bordered hover>
				<thead>
					<tr>
						<th>#</th>
						<th>Name</th>
						<th>Gender</th>
						<th colSpan={4}>Age</th>
						<th className="text-center">Action</th>
					</tr>
					<tr className="align-middle">
						<td></td>
						<td>
							<InputGroup>
								<Form.Control placeholder="Name" type="text" name="name" value={filter.name} onChange={filterUsers}/>
							</InputGroup>
						</td>
						<td className="text-center">
							<InputGroup>
								<Form.Select name="gender" value={filter.gender} onChange={filterUsers}>
									<option value="">All</option>
									<option value="Male">Male</option>
									<option value="Female">Female</option>
								</Form.Select>
							</InputGroup>
						</td>
						<td className="text-center">{filter.ageMin}</td>
						<td>
							<InputGroup>
								<Form.Range id="ageMin" name="ageMin" step="1" value={filter.ageMin} max={filter.ageMax} min="5" onChange={filterUsers}/>
							</InputGroup>
						</td>
						<td>
							<InputGroup>
								<Form.Range id="ageMax" name="ageMax" step="1" value={filter.ageMax} min={filter.ageMin} max="120" onChange={filterUsers}/>
							</InputGroup>
						</td>
						<td className="text-center">{filter.ageMax}</td>

						<td>
						<Stack direction="horizontal" gap={1} className="justify-content-center">
							<Button size="sm" variant="outline-danger" onClick={() => filterReset()}><i className="bi bi-x-lg"/></Button>
							<Button size="sm" variant="outline-success" onClick={() => props.getUsers()}><i className="bi bi-arrow-clockwise"/></Button>
						</Stack>
						</td>
					</tr>
				</thead>
				<tbody>
					{users.length === 0 ?
						<tr className="text-center"><td colSpan={8}>No users available!</td></tr>
					:
					users.map((user: User, index: number) => {
						if(modify !== modifyDefault && user.id === modify.id) {
							return (
								<tr className="align-middle" key={index}>
									<td>{index + 1}</td>
									<td>
										<InputGroup>
											<Form.Control placeholder="Name" type="text" name="name" value={modify.name} onChange={modifyUser}/>
										</InputGroup>
									</td>
									<td className="text-center">
										<InputGroup>
											<Form.Select name="gender" value={modify.gender} onChange={modifyUser}>
												<option value="Male">Male</option>
												<option value="Female">Female</option>
											</Form.Select>
										</InputGroup>
									</td>
									<td className="text-center">{modify.age}</td>
									<td colSpan={3}>
										<InputGroup>
											<Form.Range id="age" name="age" step="1" value={modify.age} min="5" max="120" onChange={modifyUser}/>
										</InputGroup>
									</td>
									<td>
										<Stack direction="horizontal" gap={1} className="justify-content-center">
											<Button size="sm" variant="outline-success" disabled={JSON.stringify(user) === JSON.stringify({...modify, age: parseInt(modify.age)})}
												onClick={() => {
													props.updateUser({...modify, age: parseInt(modify.age)})
													modifyReset()
												}}><i className="bi bi-save"/></Button>
											<Button size="sm" variant="outline-danger" onClick={() => modifyReset()}><i className="bi bi-x-lg"/></Button>
										</Stack>
									</td>
								</tr>
							)
						}
						return (
							<tr className="align-middle" key={index}>
								<td>{index + 1}</td>
								<td>{user.name}</td>
								<td>{user.gender}</td>
								<td colSpan={4}>{user.age}</td>
								<td>
									<Stack direction="horizontal" gap={1} className="justify-content-center">
										<Button size="sm" variant="outline-info" onClick={() => navigate("/users/" + user.id)}><i className="bi bi-eye"/></Button>
										<Button size="sm" variant="outline-success" onClick={() => setModify(
											{id: user.id!, name: user.name, gender: user.gender, age: user.age.toString()}
										)}><i className="bi bi-pencil"/></Button>
										<Button size="sm" variant="outline-danger" onClick={() => props.deleteUser(user.id!)}><i className="bi bi-trash3"/></Button>
									</Stack>
								</td>
							</tr>
						)
					})}
				</tbody>
			</Table>
	)
}

export default UsersTable