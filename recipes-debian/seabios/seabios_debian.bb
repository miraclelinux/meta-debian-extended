# From meta-virtualization/recipes-extended/seabios
# rev: 279dbced29e928618ba836226542926604d7e570
# url: http://git.yoctoproject.org/cgit/cgit.cgi/meta-virtualization/tree/recipes-extended/seabios?h=warrior

DESCRIPTION = "SeaBIOS"
HOMEPAGE = "http://www.coreboot.org/SeaBIOS"
LICENSE = "LGPLv3"
SECTION = "firmware"

DEBIAN_QUILT_PATCHES = ""
inherit debian-package
require recipes-debian/sources/seabios.inc

SRC_URI += " \
    file://hostcc.patch \
    "
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504         \
                    file://COPYING.LESSER;md5=6a6a8e020838b23406c81b19c1d46df6  \
                    "

FILES_${PN} = "/usr/share/seabios"

DEPENDS = "util-linux-native file-native bison-native flex-native gettext-native acpica-native python-native"

TUNE_CCARGS = ""
EXTRA_OEMAKE += "HOSTCC='${BUILD_CC}'"
EXTRA_OEMAKE += "CC='${BUILD_CC}'"
EXTRA_OEMAKE += "LD='${BUILD_LD}'"

SEABIOS_TEMP_BIOS_FIRMWARE_DIR = "${B}/bios_images"

seabios_create_bios_config() {
  oe_runmake defconfig
     
  for x in "$@"; do
    val=""
    case $x in
      (*=n) val="# CONFIG_${x%=*} is not set";; 
      (*) val="CONFIG_$x";; 
    esac;
    echo "$val" >> ${B}/.config
  done 
  oe_runmake oldnoconfig
}

seabios_build_optionroms() {
  oe_runmake -C debian/optionrom
  chmod -x debian/optionrom/*.bin

  if [ -e "debian/optionrom/vapic.bin" ]; then
    rm -f debian/optionrom/vapic.bin
  fi
  ln -s kvmvapic.bin debian/optionrom/vapic.bin  
  mv debian/optionrom/*.bin ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR}/
}

seabios_build_firmwares() {
  rm -f ${B}/src/fw/acpi-dsdt.hex ${B}/src/fw/q35-acpi-dsdt.hex ${B}/src/fw/ssdt-misc.hex ${B}/src/fw/ssdt-pcihp.hex ${B}/src/fw/ssdt-proc.hex

  oe_runmake OUT=${B}/out/ src/fw/acpi-dsdt.hex src/fw/q35-acpi-dsdt.hex src/fw/ssdt-misc.hex src/fw/ssdt-pcihp.hex src/fw/ssdt-proc.hex
  
}

do_configure() {
  oe_runmake defconfig
}

#
# Build each bios targets.
# config options came from debian/rules file.
# 
do_compile() {
    unset CPP
    unset CPPFLAGS
    
    if [ -d ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR} ]; then
      rm -fr ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR}
    fi
    mkdir ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR}
    
    for name in bios bios-256k vgabios-cirrus vgabios-stdvga vgabios-virtio vgabios-vmware vgabios-qxl vgabios-isavga; do
      oe_runmake clean
      case $name in
        "bios")
          seabios_create_bios_config "QEMU=y" "ROM_SIZE=128" "ATA_DMA=y" "PVSCSI=n" "BOOTSPLASH=n" "XEN=n" "USB_OHCI=n" "USB_XHCI=n" "USB_UAS=n" "SDCARD=n" "TCGBIOS=n" "MPT_SCSI=n" "NVME=n" "USE_SMM=n" "VGAHOOKS=n";;
	"bios-256k")
          seabios_create_bios_config "QEMU=y" "ROM_SIZE=256" "ATA_DMA=y";;
	"vgabios-cirrus")
          seabios_create_bios_config "BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=y";;
	"vgabios-stdvga")
          seabios_create_bios_config "BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=y";;
	"vgabios-virtio")
          seabios_create_bios_config "BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=y" "OVERRIDE_PCI_ID=y" "VGA_VID=0x1af4" "VGA_DID=0x1050";;
        "vgabios-vmware")
          seabios_create_bios_config "vgabios,BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=y" "OVERRIDE_PCI_ID=y" "VGA_VID=0x15ad" "VGA_DID=0x0405";;
        "vgabios-qxl")
          seabios_create_bios_config "BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=y" "OVERRIDE_PCI_ID=y" "VGA_VID=0x1b36" "VGA_DID=0x0100";;
	"vgabios-isavga")
          seabios_create_bios_config "BUILD_VGABIOS=y" "VGA_BOCHS=y" "VGA_PCI=n";;
	"*")
          bberror "unknown target ${name}";;
      esac;

      oe_runmake

      cp ${B}/out/bios.bin ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR}/${name}.bin
    done

   seabios_build_optionroms
   seabios_build_firmwares
}

do_install() {
    install -d ${D}/${prefix}/share/seabios
    install -m 0644 ${SEABIOS_TEMP_BIOS_FIRMWARE_DIR}/*.bin ${D}/${prefix}/share/seabios/
    install -m 0644 ${B}/out/src/fw/acpi-dsdt.aml ${D}/${prefix}/share/seabios/
    install -m 0644 ${B}/out/src/fw/q35-acpi-dsdt.aml ${D}/${prefix}/share/seabios/
}

FILES_${PN}_append_class-nativesdk = " ${SDKPATHNATIVE}"
BBCLASSEXTEND = "native nativesdk"