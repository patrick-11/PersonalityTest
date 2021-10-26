import React from "react"


import { Container, Table, Button } from "react-bootstrap"

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
						<th className="text-center">Results</th>
					</tr>
				</thead>
				<tbody>
					{props.users.map((user, index) => {
						return (
							<tr key = {index}>
								<td>{index + 1}</td>
								<td>{user.name}</td>
								<td>{user.gender}</td>
								<td>{user.age}</td>
								<td>{user.avgScore}</td>
								<td className="text-center">
									<Button
										size = "sm"
										onClick = {() => {props.seeResults(user)}}
									>
										Results
									</Button>
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