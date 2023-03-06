import axios from "axios"

export const ENDPOINT = "http://localhost:8080/api/v1/images"

const imageApi = {
    getAllImage() {
        return axios.get(ENDPOINT)
    },
    uploadImage(file) {
        return axios.post(ENDPOINT, file, {
            headers: {
                "Content-Type": "form-data"
            }
        })
    },
    deleteImage(id) {
        return axios.delete(`${ENDPOINT}/${id}`)
    }
}

export default imageApi;