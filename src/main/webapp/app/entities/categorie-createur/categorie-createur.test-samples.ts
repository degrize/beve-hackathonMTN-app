import { ICategorieCreateur, NewCategorieCreateur } from './categorie-createur.model';

export const sampleWithRequiredData: ICategorieCreateur = {
  id: 84244,
};

export const sampleWithPartialData: ICategorieCreateur = {
  id: 52748,
};

export const sampleWithFullData: ICategorieCreateur = {
  id: 93233,
  categorie: 'Plastic synthesize Producteur',
  description: 'navigate intuitive',
};

export const sampleWithNewData: NewCategorieCreateur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
