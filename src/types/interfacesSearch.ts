import { User } from "./interfacesRouter"

export interface SearchInfoProps {
	gender: string
	age: number
	avgScore: number
}

export interface SearchTableProps {
	users: Array<User>
	seeResults: (user: User) => void
}