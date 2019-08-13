require ${COREBASE}/meta/recipes-devtools/cmake/cmake.inc

SRC_URI = ""

inherit debian-package
require recipes-debian/sources/cmake.inc
DEBIAN_QUILT_PATCHES = ""
FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/cmake/cmake"

LIC_FILES_CHKSUM = "file://Copyright.txt;md5=f61f5f859bc5ddba2b050eb10335e013 \
                    file://Source/cmake.h;md5=4494dee184212fc89c469c3acd555a14;beginline=1;endline=3 \
"

SRC_URI += " \
           file://0002-cmake-Prevent-the-detection-of-Qt5_debian.patch \
           file://0003-cmake-support-OpenEmbedded-Qt4-tool-binary-names.patch \
           file://0004-Fail-silently-if-system-Qt-installation-is-broken.patch \
"

inherit native

DEPENDS += "bzip2-replacement-native expat-native xz-native zlib-native curl-native ncurses-native"

SRC_URI += "file://OEToolchainConfig.cmake \
            file://environment.d-cmake.sh \
            file://0001-CMakeDetermineSystem-use-oe-environment-vars-to-load.patch \
            file://0005-Disable-use-of-ext2fs-ext2_fs.h-by-cmake-s-internal-.patch \
            "


B = "${WORKDIR}/build"
do_configure[cleandirs] = "${B}"

CMAKE_EXTRACONF = "\
    -DCMAKE_LIBRARY_PATH=${STAGING_LIBDIR_NATIVE} \
    -DBUILD_CursesDialog=1 \
    -DCMAKE_USE_SYSTEM_LIBRARIES=1 \
    -DCMAKE_USE_SYSTEM_LIBRARY_JSONCPP=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBARCHIVE=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBUV=0 \
    -DCMAKE_USE_SYSTEM_LIBRARY_LIBRHASH=0 \
    -DENABLE_ACL=0 -DHAVE_ACL_LIBACL_H=0 \
    -DHAVE_SYS_ACL_H=0 \
"

do_configure () {
	${S}/configure --verbose --prefix=${prefix} \
		${@oe.utils.parallel_make_argument(d, '--parallel=%d')} \
		${@bb.utils.contains('CCACHE', 'ccache ', '--enable-ccache', '', d)} \
		-- ${CMAKE_EXTRACONF}
}

do_compile() {
	oe_runmake
}

do_install() {
	oe_runmake 'DESTDIR=${D}' install

	# The following codes are here because eSDK needs to provide compatibilty
	# for SDK. That is, eSDK could also be used like traditional SDK.
	mkdir -p ${D}${datadir}/cmake
	install -m 644 ${WORKDIR}/OEToolchainConfig.cmake ${D}${datadir}/cmake/
	mkdir -p ${D}${base_prefix}/environment-setup.d
	install -m 644 ${WORKDIR}/environment.d-cmake.sh ${D}${base_prefix}/environment-setup.d/cmake.sh
}

do_compile[progress] = "percent"

SYSROOT_DIRS_NATIVE += "${datadir}/cmake ${base_prefix}/environment-setup.d"
