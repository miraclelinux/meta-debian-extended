require attr.inc

# configure.ac was missing from the release tarball. This should be fixed in
# future releases of attr, remove this when updating the recipe.
SRC_URI += "file://attr-Missing-configure.ac.patch \
            file://dont-use-decl-macros.patch \
            file://Remove-the-section-2-man-pages.patch \
            file://Remove-the-attr.5-man-page-moved-to-man-pages.patch \
            file://0001-Use-stdint-types-consistently.patch \
           "

BBCLASSEXTEND = "native nativesdk"
