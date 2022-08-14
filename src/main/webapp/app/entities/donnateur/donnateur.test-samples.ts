import dayjs from 'dayjs/esm';

import { Sexe } from 'app/entities/enumerations/sexe.model';
import { Forfait } from 'app/entities/enumerations/forfait.model';

import { IDonnateur, NewDonnateur } from './donnateur.model';

export const sampleWithRequiredData: IDonnateur = {
  id: 71533,
  nomDeFamille: 'navigate Saint-Dominique quantifying',
  contact1: 'Health program',
};

export const sampleWithPartialData: IDonnateur = {
  id: 32775,
  nomDeFamille: 'tan Developpeur',
  contact1: 'card',
  contact2: 'Account bluetooth',
  sexe: Sexe['JE_PREFERE_NE_PAS_LE_DIRE'],
  forfait: Forfait['JOUR'],
};

export const sampleWithFullData: IDonnateur = {
  id: 80832,
  nomDeFamille: 'mint calculating monitor',
  prenom: 'bypass bluetooth engineer',
  contact1: 'implement pink',
  contact2: 'Franche-Comté',
  email: 'Alos.Gaillard@hotmail.fr',
  sexe: Sexe['JE_PREFERE_NE_PAS_LE_DIRE'],
  dateDeNaissance: 'out-of-the-box',
  pays: 'driver Som',
  dateDebut: dayjs('2022-08-13'),
  forfait: Forfait['ANNEE'],
};

export const sampleWithNewData: NewDonnateur = {
  nomDeFamille: 'transmitting initiatives',
  contact1: 'ivory e-business Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
