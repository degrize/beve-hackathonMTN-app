import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

export interface ISouscription {
  id: number;
  etat?: EtatCompte | null;
  montant?: number | null;
  pourcentageDuDon?: number | null;
}

export type NewSouscription = Omit<ISouscription, 'id'> & { id: null };
