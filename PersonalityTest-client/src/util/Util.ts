export const errorCheck = (error: any) => {
  if(typeof error.response.data !== "undefined" &&
  typeof error.response.data.details !== "undefined" &&
  typeof error.response.data.details[0] === "string") {
    return error.response.data.details[0]
  }
  return "Server is not reachable at the moment!"
}