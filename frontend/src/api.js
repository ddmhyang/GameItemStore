import axios from 'axios';

const client = axios.create({
  baseURL: 'http://localhost:8080/api'
});

export const loginApi = (data) => client.post('/members/login', data);
export const joinApi = (data) => client.post('/members/join', data);
export const withdrawApi = (id, amount) => client.post(`/members/${id}/withdraw?amount=${amount}`);
export const getMembersApi = () => client.get('/members');
export const banMemberApi = (id) => client.delete(`/members/${id}`);

export const getItemsApi = (keyword) => {
  const url = keyword ? `/items?keyword=${keyword}` : '/items';
  return client.get(url);
};
export const addItemApi = (sellerId, data) => client.post(`/items?sellerId=${sellerId}`, data);
export const buyItemApi = (itemId, buyerId) => client.post(`/items/${itemId}/buy?buyerId=${buyerId}`);
export const deleteItemApi = (itemId) => client.delete(`/items/${itemId}`);
export const getMySellingApi = (memberId) => client.get(`/items/my-selling?memberId=${memberId}`);
export const getMyBuyingApi = (memberId) => client.get(`/items/my-buying?memberId=${memberId}`);
export const updateMileageApi = (id, amount) => client.put(`/members/${id}/mileage?amount=${amount}`);
export const updateItemApi = (id, data) => client.put(`/items/${id}`, data);