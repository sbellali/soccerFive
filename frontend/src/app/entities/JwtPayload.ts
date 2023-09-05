import { User } from "./user";


export interface JwtPayload {
    iat: number,
    iss: string,
    exp: number,
    roles: string,
    sub: string,
    user: User
}