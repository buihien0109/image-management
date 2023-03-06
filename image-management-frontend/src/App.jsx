import { useEffect, useState } from "react";
import imageApi, { ENDPOINT } from "./apis/imageApi";

function App() {
    // State chứa danh sách ảnh
    const [images, setImages] = useState([]);

    useEffect(() => {
        // Lấy danh sách ảnh ban đầu
        const fetchImages = async () => {
            try {
                // Gọi API lấy danh sách ảnh
                const rs = await imageApi.getAllImage();

                // Lưu kết quả vào trong state
                setImages(rs.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchImages();
    }, []);

    // Xử lý upload ảnh
    const handleUploadImage = async (e) => {
        // Lấy ra đối tượng file upload
        const file = e.target.files[0];

        // Tạo đối tượng formData chứa thông tin file upload
        const formData = new FormData();
        formData.append("file", file);

        try {
            // Gọi API upload image
            let rs = await imageApi.uploadImage(formData);

            // Lưu kết quả sau khi gọi API vào trong state
            setImages([rs.data, ...images]);
            alert("Upload ảnh thành công");
        } catch (error) {
            console.log(error);
        }
    };

    // Xử lý xóa ảnh
    const handleDeleteImage = async (id) => {
        // Xác nhận xem người dùng có muốn xóa không
        const isConfirm = window.confirm("Bạn có muốn xóa không");
        if (!isConfirm) return;
        try {
            // Gọi API xóa ảnh
            await imageApi.deleteImage(id);

            // Xóa ảnh trong state
            const newImages = images.filter((image) => image.id !== id);
            setImages(newImages);

            alert("Xóa ảnh thành công");
        } catch (error) {
            console.log(error);
        }
    };

    return (
        <>
            <div className="container py-5">
                <div className="mb-4">
                    <label htmlFor="input" className="btn btn-primary">
                        Upload image
                    </label>
                    <input
                        type="file"
                        className="d-none"
                        id="input"
                        onChange={handleUploadImage}
                    />
                </div>
                <h2 className="text-center">List Image</h2>
                <div className="py-4">
                    <div className="row">
                        {images.map((image) => (
                            <div key={image.id} className="col-md-3">
                                <div className="text-center mb-5">
                                    <img
                                        src={`${ENDPOINT}/${image.id}`}
                                        alt={image.name}
                                    />
                                    <a
                                        href={`${ENDPOINT}/download/${image.id}`}
                                        download={image.name}
                                        className="btn btn-warning mt-2 me-2"
                                    >
                                        Download
                                    </a>
                                    <button
                                        className="btn btn-danger mt-2"
                                        onClick={() =>
                                            handleDeleteImage(image.id)
                                        }
                                    >
                                        Delete
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </>
    );
}

export default App;
