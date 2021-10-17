export interface ResultFilterProps {
	onChangeFilter: (gender: Array<string>, age: AgeRange) => void
}

export type AgeRange = {
	minAge: string
	maxAge: string
}

export interface SearchInfoProps {
	gender: string
	age: number
	avgScore: number
}