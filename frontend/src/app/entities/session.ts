import { User } from "./user";

export interface Session {
    id: number,
    startTime: string,
    duration: number,
    location: string,
    maxPlayers: number,
    price: number,
    description: string,
    organizer: User,
    players: User[]
}