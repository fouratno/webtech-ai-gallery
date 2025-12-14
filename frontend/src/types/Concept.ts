export interface Concept {
  id?: number;
  title: string;
  promptArtist?: string;
  aiTool?: string;
  imageUrl?: string;
}

export interface ConceptFormData {
  title: string;
  promptArtist: string;
  aiTool: string;
  imageUrl: string;
}

export interface ApiResponse<T> {
  data?: T;
  error?: string;
  status: number;
}