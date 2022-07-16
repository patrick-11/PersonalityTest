import React from "react"


import { Container, Table } from "react-bootstrap"

import ButtonCenter from "./buttonCenter"

import { SearchTableProps } from "../types/interfacesSearch"


const searchTable = (props: SearchTableProps) => {
	return (
		<Container>
			<Table striped bordered hover>
				<thead>
					<tr>
						<th>#</th>
						<th>Name</th>
						<th>Gender</th>
						<th>Age</th>
						<th>Score</th>
						<th className="text-center">Action</th>
					</tr>
				</thead>
				<tbody>
					{props.users.map((user, index) => {

						const resultsButtonAction = () => {props.seeResults(user)}

						return (
							<tr key = {index}>
								<td>{index + 1}</td>
								<td>{user.name}</td>
								<td>{user.gender}</td>
								<td>{user.age}</td>
								<td>{user.avgScore}</td>
								<td>
									<ButtonCenter
										name = {"Results"}
										size = {"sm"}
										variant = {"outline-info"}
										action = {resultsButtonAction}
									/>
								</td>
							</tr>
						)
					})}
				</tbody>
			</Table>
		</Container>
	)
}

export default searchTable