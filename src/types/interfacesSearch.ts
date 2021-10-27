import { User } from "./interfacesRouter"

export interface SearchBarProps {
	name: string
	filterUsers: (event: any) => void
	searchReset: () => void
}

export interface SearchInfoProps {
	name: string
	gender: string
	age: number
	avgScore: number
}

export interface SearchTableProps {
	users: Array<User>
	seeResults: (user: User) => void
}