import dayjs from 'dayjs/esm';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Forfait } from 'app/entities/enumerations/forfait.model';

export interface IDonnateur {
  id: number;
  nomDeFamille?: string | null;
  prenom?: string | null;
  contact1?: string | null;
  contact2?: string | null;
  email?: string | null;
  sexe?: Sexe | null;
  dateDeNaissance?: string | null;
  pays?: string | null;
  dateDebut?: dayjs.Dayjs | null;
  forfait?: Forfait | null;
}

export type NewDonnateur = Omit<IDonnateur, 'id'> & { id: null };
