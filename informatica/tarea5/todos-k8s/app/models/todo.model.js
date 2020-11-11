const mongoose = require('mongoose');

const TodoSchema = mongoose.Schema({
    title: String,
    content: String,
    completed: Boolean
}, {
    timestamps: true
});

module.exports = mongoose.model('Todo', TodoSchema);