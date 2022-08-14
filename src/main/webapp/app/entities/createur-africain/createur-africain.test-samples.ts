import dayjs from 'dayjs/esm';

import { Sexe } from 'app/entities/enumerations/sexe.model';
import { SituationMatrimoniale } from 'app/entities/enumerations/situation-matrimoniale.model';

import { ICreateurAfricain, NewCreateurAfricain } from './createur-africain.model';

export const sampleWithRequiredData: ICreateurAfricain = {
  id: 78490,
  nomDeFamille: 'tangible b capacitor',
  prenom: 'Vision-oriented a Centre',
  label: 'c',
  contact1: 'Licensed monitor',
  sexe: Sexe['F'],
  email: 'Savinien.Leroy84@yahoo.fr',
  pays: 'pink',
};

export const sampleWithPartialData: ICreateurAfricain = {
  id: 22603,
  nomDeFamille: 'Centre superstructure',
  prenom: 'c convergence Industrial',
  label: 'portal',
  surnom: 'deposit Unbranded',
  contact1: 'Streamlined connect',
  sexe: Sexe['M'],
  email: 'Armandine_Olivier@hotmail.fr',
  pays: 'Checking real-time invoice',
  ville: 'Cotton',
  dateDebut: dayjs('2022-08-13'),
};

export const sampleWithFullData: ICreateurAfricain = {
  id: 20000,
  nomDeFamille: 'Bedfordshire EXE Analyste',
  prenom: 'virtual robust',
  label: 'violet',
  surnom: 'Fresh supply-chains virtual',
  contact1: 'azure Mouse Fish',
  contact2: 'strategize',
  sexe: Sexe['F'],
  email: 'Thomas_Fournier61@yahoo.fr',
  dateDeNaissance: 'Pizza primary Soft',
  pays: 'Tasty c b',
  ville: 'Joubert analyzing',
  adresse: 'Fantastic',
  situationMatrimoniale: SituationMatrimoniale['CONCUBINAGE'],
  dateDebut: dayjs('2022-08-13'),
};

export const sampleWithNewData: NewCreateurAfricain = {
  nomDeFamille: 'Sausages Profit-focused',
  prenom: 'portals wireless Île-de-France',
  label: 'olive',
  contact1: 'Focused',
  sexe: Sexe['M'],
  email: 'Landre_Bonnet@yahoo.fr',
  pays: 'Handcrafted reintermediate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
