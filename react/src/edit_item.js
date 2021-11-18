import './App.css';
import React from 'react';
import Header from './header';
import './home.css';
import './sell.css';
import { handleAPIError } from './errors';


class Edit_Item extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            price: '',
            category: 'none',
            description: '',
            images: [],
            imageFiles: [],
            imageIds: [],
            location: 'none',
            owner: '',
            loaded: false,
            valid: true,
            phone: ''
        };
        this.changeName = this.changeName.bind(this);
        this.changePrice = this.changePrice.bind(this);
        this.changeCategory = this.changeCategory.bind(this);
        this.changeDescription = this.changeDescription.bind(this);
        this.uploadImage = this.uploadImage.bind(this);
        this.changeLocation = this.changeLocation.bind(this);
        this.changePhone = this.changePhone.bind(this)

        this.getImages = this.getImages.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    changePhone(event) {
        this.setState({phone: event.target.value})
    }
    
    componentDidMount() {
        const id = this.props.match.params.id;
        this.setState({
            id: id
        })
        const requestOptions = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }

        };

        fetch(`/api/getItem/${id}`, requestOptions)
            .then(res=>{
                if (res.status !== 200) {
                    handleAPIError(res);
                    /*
                    this.setState({
                        name: `Item Name ${id}`,
                        description: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                        price: 25.0,
                        images: ["https://images.unsplash.com/photo-1626885228113-0ac4b52e6cea?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2070&q=80", "https://images.unsplash.com/photo-1593642632559-0c6d3fc62b89?ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2069&q=80"],
                        location: "Student Union",
                        category: "Electronics",
                        loaded: true
                    })
                    */
                  
                } else {
                    res.json().then(data => {
                        const email = localStorage.getItem("email");
                        const item = data.item;
                        if (item.owner.userId !== email) {
                            this.setState({ 
                                loaded: true,
                                valid: false
                            })
                        } else {
                            this.setState({
                                name: item.name,
                                description: item.description,
                                price: item.price,
                                images: item.images,
                                location: item.meetingPlace,
                                category: item.category,
                                loaded: true,
                                imageIds: item.imageIds ? item.imageIds : [],
                                owner: email,
                                phone: item.contactPhoneNumber ? item.contactPhoneNumber.replace(/[()]/g, '').replace(/-/g, ' ') : ''
                            })
                        }
                    })
                }
        })
        console.log(this.state);
    }

    changeName(event) {
        this.setState({name: event.target.value});
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
        if (!email || email !== this.state.owner) {
            alert("You do not have permission to edit this listing.");
            return;
        }
        
        this.getImages(email)
            .then(imageIds => {
                const requestOptions = {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ 
                        userId: email,
                        item: {
                            itemId: this.state.id,
                            name: this.state.name,
                            category: this.state.category,
                            description: this.state.description,
                            price: parseFloat(this.state.price),
                            images: this.state.imageIds.concat(imageIds),
                            meetingPlace: this.state.location,
                            contactPhoneNumber: this.state.phone.replace(/\s/g, '')
                        }
                     })
                };
                fetch('/api/editItem', requestOptions)
                    .then(response => {
                        this.props.history.push(`/item/${this.state.id}`);
                    });           
                })
    }
    
    render() {

        let images = this.state.images.map((src, idx) => <img key={idx} alt={`${this.state.name}`} src={src}/>)

        return (
            this.state.loaded ? this.state.valid ? <div className="sell">
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
                        <textarea name="description" value={this.state.description} onChange={this.changeDescription} />
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
            </div> : <div>You do not have access to this item.</div> : <div>loading...</div>
        );
    }
}

export default Edit_Item;