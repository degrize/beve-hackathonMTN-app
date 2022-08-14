import { IReseauxSociaux, NewReseauxSociaux } from './reseaux-sociaux.model';

export const sampleWithRequiredData: IReseauxSociaux = {
  id: 87034,
  nom: 'Avon high-level leading',
};

export const sampleWithPartialData: IReseauxSociaux = {
  id: 77972,
  nom: 'tangible 3rd Towels',
  lienChaine: 'SDD',
};

export const sampleWithFullData: IReseauxSociaux = {
  id: 25336,
  nom: 'du lime c',
  nomChaine: 'Berkshire',
  lienChaine: 'Tenge ivory',
};

export const sampleWithNewData: NewReseauxSociaux = {
  nom: 'methodical',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
