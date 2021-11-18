import './App.css';
import React from 'react';
import Header from './header';
import './home.css';
import './sell.css';
import { handleAPIError } from './errors';

class Sell extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            price: '',
            category: 'none',
            description: '',
            images: [],
            imageFiles: [],
            location: 'none',
            phone: ''
        };
        this.changeName = this.changeName.bind(this);
        this.changePrice = this.changePrice.bind(this);
        this.changeCategory = this.changeCategory.bind(this);
        this.changeDescription = this.changeDescription.bind(this);
        this.uploadImage = this.uploadImage.bind(this);
        this.changeLocation = this.changeLocation.bind(this);
        this.changePhone = this.changePhone.bind(this);

        this.getImages = this.getImages.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    changeName(event) {
        this.setState({name: event.target.value});
    }

    changePhone(event) {
        this.setState({phone: event.target.value})
    }

    changePrice(event) {
        // Remove whitespaces
        let price = event.target.value.replace(/\s/g, "");
        // Reject input if it makes the price not a number
        if (isNaN(price)) return;

        if (price === '') {
            this.setState({ price: price })
            return;
        }

        // Get the specific dollars/cents
        let [dollars, cents] = price.split('.');
        // Remove leading 0's
        dollars = parseInt(dollars);
        if (cents) {
            // Remove fractions of cents
            if (cents.length > 2) {
                cents = cents.substring(0, 2);
            }
            price = `${dollars}.${cents}`;
        } else {
            // If the most recent character is a period, keep it.
            if (cents === '') {
                price = `${dollars}.`;
            } else price = dollars.toString();
        }
        this.setState({price: price})
    }

    changeCategory(event) {
        if (event.target.value === 'none') return;
        this.setState({category: event.target.value});
    }

    changeDescription(event) {
        this.setState({description: event.target.value});
    }

    uploadImage(event) {
        const file = event.target.files[0];
        
        if (file.type.split('/')[0] !== 'image') {
            alert("Invalid file type!");
            return;
        }

        if (file.size > 10000000) {
            alert("Uploaded file is too big! Please upload a file less than 10MB");
        }
        const objectURL = URL.createObjectURL(file);
        const newImageList = this.state.images.concat(objectURL);
        const newImageFileList = this.state.imageFiles.concat(file);
        this.setState({ 
            images: newImageList,
            imageFiles: newImageFileList 
        })
    }

    changeLocation(event) {
        if (event.target.value === 'none') return;
        this.setState({location: event.target.value});
    }

    async getImages(email) {
        let imagePromises = [];
        for (let file of this.state.imageFiles) {
            imagePromises.push(new Promise((resolve) => {
                const reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function () {
                    let base64 = reader.result.split('base64,')[1];
                    let imageRequestOptions = {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ 
                            userId: email,
                            image: base64
                        })
                    };
                    fetch('/api/image/upload', imageRequestOptions)
                        .then(response => {
                            if (response.status !== 200) {
                                handleAPIError(response, false)
                                resolve(null);
                            } else {
                                response.json().then(data => {
                                    resolve(data.image.imageId);
                                });
                            }
                        })
                };
                reader.onerror = function (error) {
                    alert('Error: ', error);
                    resolve(null);
                };
            }));
        }
        return Promise.all(imagePromises);
    }

    async handleSubmit(event) {
        event.preventDefault();
        const email = localStorage.getItem("email");
        if (!email) {
            alert("You must log in to list an item!");
            return;
        }
        
        this.getImages(email)
            .then(imageIds => {
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ 
                        name: this.state.name,
                        userId: email,
                        category: this.state.category,
                        description: this.state.description,
                        price: parseFloat(this.state.price),
                        images: imageIds,
                        meetingPlace: this.state.location,
                        contactPhoneNumber: this.state.phone.replace(/\s/g, '')
                     })
                };
                fetch('/api/newItem', requestOptions)
                    .then(response => {
                        this.props.history.push(`/`);
                    });
            
                })
    }
    
    render() {

        let images = this.state.images.map((src, idx) => <img key={idx} alt={`${this.state.name}`} src={src}/>)

        return (
            <div className="sell">
                <Header />
                <form onSubmit={this.handleSubmit}>
                    <label>
                        <div>Item Name</div>
                        <input type="text" name="name" value={this.state.name} onChange={this.changeName} required />
                    </label>

                    <label>
                        <div>Item Price</div>
                        <input type="text" name="price" placeholder='$$$' value={this.state.price} onChange={this.changePrice} required />
                    </label>

                    <label>
                        <div>Item Category</div>
                        <select name="category" value={this.state.category} onChange={this.changeCategory} required>
                            <option value='none' disabled>Select a Category...</option>
                            <option value="electronics">Electronics</option>
                            <option value="textbooks">Textbooks</option>
                            <option value="clothes">Clothes</option>
                            <option value="furniture">Furniture</option>
                        </select>
                    </label>

                    <label>
                        <div>Item Description</div>
                        <textarea name="description" onChange={this.changeDescription} />
                    </label>

                    <label htmlFor="image">
                        <div>Upload Photo</div>
                        <div className="images">
                            <div className="add">+</div>
                            {images}
                        </div>
                    </label>
                    <input type="file" name="image" id="image" onChange={this.uploadImage} />

                    <label>
                        <div>Meetup Locations</div>
                        <select name="locations" value={this.state.location} onChange={this.changeLocation} required>
                            <option value='none' disabled>Select a Location...</option>
                            <option value='Natural Science Complex'>Natural Science Complex</option>
                            <option value='Capen'>Capen</option>
                            <option value='Norton'>Norton</option>
                        </select>
                    </label>

                    <label>
                        <div>Phone Number</div>
                        <input type="tel" pattern="[0-9]{3} [0-9]{3} [0-9]{4}" name="price" placeholder='123 456 7890' value={this.state.phone} onChange={this.changePhone} />
                    </label>

                    <input type="submit" value="Submit" />
                </form>
            </div>
        );
    }
}

export default Sell;