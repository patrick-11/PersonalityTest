export interface ResultFilterProps {
	onChangeFilter: (gender: Array<string>, age: AgeRange) => void
}

export type AgeRange = {
	minAge: string
	maxAge: string
}