import dayjs from 'dayjs/esm';
import { IInspiration } from 'app/entities/inspiration/inspiration.model';
import { ISouscription } from 'app/entities/souscription/souscription.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

export interface ICreateurAfricain {
  id: number;
  nomDeFamille?: string | null;
  prenom?: string | null;
  label?: string | null;
  surnom?: string | null;
  contact1?: string | null;
  contact2?: string | null;
  sexe?: Sexe | null;
  email?: string | null;
  dateDeNaissance?: string | null;
  pays?: string | null;
  ville?: string | null;
  adresse?: string | null;
  situationMatrimoniale?: SituationMatrimoniale | null;
  dateDebut?: dayjs.Dayjs | null;
  inspirations?: Pick<IInspiration, 'id' | 'nom'>[] | null;
  souscriptions?: Pick<ISouscription, 'id' | 'etat'>[] | null;
}

export type NewCreateurAfricain = Omit<ICreateurAfricain, 'id'> & { id: null };
