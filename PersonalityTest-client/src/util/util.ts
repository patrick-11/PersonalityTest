export const errorCheck = (error: any) => {
	if(typeof error.response.data !== "undefined" &&
  typeof error.response.data.details !== "undefined" &&
  typeof error.response.data.details[0] === "string") {
		return error.response.data.details[0]
	}
	return "Server is not reachable at the moment!"
}

export const convertTimestamp = (timestamp: string) => {
	const year = timestamp.substring(2, 4)
	const month = timestamp.substring(5, 7)
	const day = timestamp.substring(8, 10)
	const hour = timestamp.substring(11, 13)
	const minutes = timestamp.substring(14, 16)
	return day + "." + month + "." + year + "-" + hour + ":" + minutes
}