import { User } from "./user";


export interface JwtPayload {
    iat: number,
    iss: string,
    roles: string,
    sub: string,
    user: User
}