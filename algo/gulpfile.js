var gulp = require('gulp');
var sass = require('gulp-sass');
var concat = require('gulp-concat');

var scss = 'styles/scss/**/*.scss';
var css = 'styles/css/*.css'

gulp.task('compile-sass', function() {
    console.log("compiling sass...");
    return gulp.src(scss)
        .pipe(sass())
        .pipe(gulp.dest('styles/css/'));
});

gulp.task('concat-css', ['compile-sass'], function() {
    return gulp.src(css)
        .pipe(concat('styles.css'))
        .pipe(gulp.dest('styles'));
})

gulp.task('watch', function() {
    gulp.watch('styles/scss/**/*.scss', ['concat-css']);
})
